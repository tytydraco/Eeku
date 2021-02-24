package com.draco.eeku.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.audiofx.AudioEffect
import android.media.audiofx.Equalizer
import android.os.IBinder
import android.util.Log
import androidx.preference.PreferenceManager
import com.draco.eeku.R
import com.draco.eeku.repositories.Presets
import com.draco.eeku.utils.Eeku
import com.draco.eeku.views.ConfigActivity

class EekuManagerService : Service() {
    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var notificationManager: NotificationManager

    private var sessionEekuMap = mutableMapOf<Int, Eeku>()

    companion object {
        const val NOTIFICATION_ID = 1
    }

    override fun onDestroy() {
        sessionEekuMap.values.forEach { it.disable() }
        super.onDestroy()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            getString(R.string.notif_channel_id),
            getString(R.string.notif_channel_title),
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
    }

    private fun createNotification() {
        val pendingIntent = Intent(this, ConfigActivity::class.java).let { notificationIntent ->
            PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
        }

        val notification = Notification.Builder(this, getString(R.string.notif_channel_id))
            .setContentTitle(getString(R.string.notif_title))
            .setContentText(getString(R.string.notif_text))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(NOTIFICATION_ID, notification)
    }

    private fun cancelNotification() {
        stopForeground(true)
        notificationManager.cancel(NOTIFICATION_ID)
    }

    override fun onCreate() {
        super.onCreate()
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createEeku(sessionId: Int) {
        val savedId = sharedPrefs.getString(getString(R.string.pref_key_preset_id), null) ?: Presets[0].id
        val savedPreset = Presets.find { it.id == savedId } ?: Presets[0]

        /* Chose flat preset, nothing needed */
        if (savedPreset == Presets[0])
            return

        Log.d("Eeku", "Setup Eeku (${savedPreset.title}) sessionId: $sessionId")
        val eeku = Eeku(sessionId, savedPreset).also {
            it.enable()
        }

        sessionEekuMap[sessionId] = eeku

        if (notificationManager.activeNotifications.isEmpty() && sessionEekuMap.isNotEmpty()) {
            createNotificationChannel()
            createNotification()
        }
    }

    private fun destroyEeku(sessionId: Int) {
        Log.d("Eeku", "Killed Eeku sessionId: $sessionId")
        sessionEekuMap[sessionId]?.disable()
        sessionEekuMap.remove(sessionId)

        if (sessionEekuMap.isEmpty())
            cancelNotification()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val sessionId = intent.getIntExtra(Equalizer.EXTRA_AUDIO_SESSION, -1)

        when (intent.action) {
            AudioEffect.ACTION_OPEN_AUDIO_EFFECT_CONTROL_SESSION -> createEeku(sessionId)
            AudioEffect.ACTION_CLOSE_AUDIO_EFFECT_CONTROL_SESSION -> destroyEeku(sessionId)
        }

        return super.onStartCommand(intent, flags, startId)
    }
}
