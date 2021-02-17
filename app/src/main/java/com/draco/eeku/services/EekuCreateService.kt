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
    private var eeku: Eeku? = null
    private lateinit var notificationManager: NotificationManager
    private var sessionId = -1

    companion object {
        private const val NOTIFICATION_ID = 1
        private const val NOTIFICATION_CHANNEL_ID = "EekuChannel"
    }

    override fun onDestroy() {
        destroyEeku()
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

    private fun createNotification() {
        val pendingIntent = Intent(this, ConfigActivity::class.java).let { notificationIntent ->
            PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
        }

        val notification = Notification.Builder(this, NOTIFICATION_CHANNEL_ID)
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

    private fun createEeku() {
        val savedId = sharedPrefs.getString("preset_id", "mask")!!
        val savedPreset = Presets.find { it.id == savedId } ?: Presets[0]

        Log.d("Eeku", "Setup Eeku (${savedPreset.displayName}) sessionId: $sessionId")
        eeku = Eeku(sessionId, savedPreset).also {
            it.enable()
        }

        if (notificationManager.activeNotifications.isEmpty()) {
            createNotificationChannel()
            createNotification()
        }
    }

    private fun destroyEeku() {
        Log.d("Eeku", "Killed Eeku sessionId: $sessionId")
        cancelNotification()
        eeku?.disable()
        eeku = null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        sessionId = intent.getIntExtra(Equalizer.EXTRA_AUDIO_SESSION, -1)
        destroyEeku()

        if (intent.action == AudioEffect.ACTION_OPEN_AUDIO_EFFECT_CONTROL_SESSION)
            createEeku()

        return super.onStartCommand(intent, flags, startId)
    }
}
