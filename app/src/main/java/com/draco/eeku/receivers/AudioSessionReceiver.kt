package com.draco.eeku.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.audiofx.AudioEffect
import android.media.audiofx.Equalizer
import android.widget.Toast
import com.draco.eeku.services.EekuCreateService

class AudioSessionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != AudioEffect.ACTION_OPEN_AUDIO_EFFECT_CONTROL_SESSION)
            return

        val sessionId = intent.getIntExtra(Equalizer.EXTRA_AUDIO_SESSION, -1)
        if (sessionId == -1)
            return

        val eekuIntent = Intent(context, EekuCreateService::class.java)
            .putExtra(Equalizer.EXTRA_AUDIO_SESSION, sessionId)
        context.startForegroundService(eekuIntent)
    }
}