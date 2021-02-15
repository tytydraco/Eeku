package com.draco.eeku.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.audiofx.AudioEffect
import android.media.audiofx.Equalizer
import androidx.preference.PreferenceManager
import com.draco.eeku.models.Preset
import com.draco.eeku.models.Presets
import com.draco.eeku.utils.Eeku

class AudioSessionReceiver : BroadcastReceiver() {
    private fun getSavedPreset(context: Context): Preset {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        val savedId = sharedPrefs.getString("preset_id", "mask")!!
        return Presets.find { it.id == savedId } ?: Presets[0]
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == AudioEffect.ACTION_OPEN_AUDIO_EFFECT_CONTROL_SESSION) {
            val id = intent.getIntExtra(Equalizer.EXTRA_AUDIO_SESSION, -1)
            if (id != -1)
                Eeku(id, getSavedPreset(context))
        }
    }
}