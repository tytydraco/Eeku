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

class EekuCreateService : Service() {
    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var notificationManager: NotificationManager

    private var sessionEekuMap = mutableMapOf<Int, Eeku>()

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "EekuChannel"
    }

    override fun onDestroy() {
        sessionEekuMap.values.forEach { it.disable() }
        super.onDestroy()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            getString(R.string.notif_channel_title),
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
    }

    private fun createNotification(sessionId: Int) {
        val pendingIntent = Intent(this, ConfigActivity::class.java).let { notificationIntent ->
            PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
        }

        val notification = Notification.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(getString(R.string.notif_title))
            .setContentText(getString(R.string.notif_text) + sessionId.toString())
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(sessionId, notification)
    }

    private fun cancelNotification(sessionId: Int) {
        stopForeground(true)
        notificationManager.cancel(sessionId)
    }

    override fun onCreate() {
        super.onCreate()
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createEeku(sessionId: Int) {
        val savedId = sharedPrefs.getString("preset_id", "mask")!!
        val savedPreset = Presets.find { it.id == savedId } ?: Presets[0]

        Log.d("Eeku", "Setup Eeku (${savedPreset.displayName}) sessionId: $sessionId")
        val eeku = Eeku(sessionId, savedPreset).also {
            it.enable()
        }

        if (notificationManager.activeNotifications.isEmpty()) {
            createNotification(sessionId)
        }

        sessionEekuMap[sessionId] = eeku
    }

    private fun destroyEeku(sessionId: Int) {
        Log.d("Eeku", "Killed Eeku sessionId: $sessionId")
        cancelNotification(sessionId)
        sessionEekuMap[sessionId]?.disable()
        sessionEekuMap.remove(sessionId)
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
