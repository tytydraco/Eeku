package com.draco.eeku.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
    private lateinit var eeku: Eeku
    private lateinit var notificationManager: NotificationManager
    private var sessionId = -1

    companion object {
        private const val NOTIFICATION_ID = 1
        private const val NOTIFICATION_CHANNEL_ID = "EekuChannel"
    }

    override fun onDestroy() {
        eeku.disable()
        notificationManager.cancel(NOTIFICATION_ID)
        Log.d("Eeku", "Killed Eeku sessionId: $sessionId")
        super.onDestroy()
    }

    private fun createNotificationChannel() {
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
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

    override fun onCreate() {
        super.onCreate()
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)

        createNotificationChannel()
        createNotification()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        sessionId = intent.getIntExtra(Equalizer.EXTRA_AUDIO_SESSION, -1)
        val savedId = sharedPrefs.getString("preset_id", "mask")!!
        val savedPreset = Presets.find { it.id == savedId } ?: Presets[0]

        Log.d("Eeku", "Setup Eeku (${savedPreset.displayName}) sessionId: $sessionId")
        eeku = Eeku(sessionId, savedPreset).also {
            it.enable()
        }

        return super.onStartCommand(intent, flags, startId)
    }
}
