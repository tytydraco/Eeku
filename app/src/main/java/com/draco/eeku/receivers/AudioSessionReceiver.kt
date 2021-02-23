package com.draco.eeku.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.audiofx.AudioEffect
import android.media.audiofx.Equalizer
import androidx.preference.PreferenceManager
import com.draco.eeku.R
import com.draco.eeku.services.EekuManagerService

class AudioSessionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val sessionId = intent.getIntExtra(Equalizer.EXTRA_AUDIO_SESSION, -1)
        if (sessionId == -1)
            return

        val eekuIntent = Intent(context, EekuManagerService::class.java)
                .putExtra(Equalizer.EXTRA_AUDIO_SESSION, sessionId)

        when (intent.action) {
            AudioEffect.ACTION_OPEN_AUDIO_EFFECT_CONTROL_SESSION -> {
                eekuIntent.action = AudioEffect.ACTION_OPEN_AUDIO_EFFECT_CONTROL_SESSION
                val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
                if (!sharedPrefs.getBoolean(context.getString(R.string.pref_key_enabled), true))
                    return
                context.startForegroundService(eekuIntent)
            }

            AudioEffect.ACTION_CLOSE_AUDIO_EFFECT_CONTROL_SESSION -> {
                eekuIntent.action = AudioEffect.ACTION_CLOSE_AUDIO_EFFECT_CONTROL_SESSION
                context.startForegroundService(eekuIntent)
            }
        }
    }
}