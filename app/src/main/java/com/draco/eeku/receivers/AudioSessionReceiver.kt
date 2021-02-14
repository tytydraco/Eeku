package com.draco.eeku.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.audiofx.AudioEffect
import android.media.audiofx.Equalizer
import com.draco.eeku.utils.Eeku
import com.draco.eeku.utils.PresetResolver

class AudioSessionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == AudioEffect.ACTION_OPEN_AUDIO_EFFECT_CONTROL_SESSION) {
            val id = intent.getIntExtra(Equalizer.EXTRA_AUDIO_SESSION, -1)
            if (id != -1)
                Eeku(id, PresetResolver.getSavedPreset(context))
        }
    }
}