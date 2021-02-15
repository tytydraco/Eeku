package com.draco.eeku.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.audiofx.AudioEffect
import android.media.audiofx.Equalizer
import android.widget.Toast
import androidx.preference.PreferenceManager
import com.draco.eeku.models.Preset
import com.draco.eeku.repositories.Presets
import com.draco.eeku.utils.Eeku

class AudioSessionReceiver : BroadcastReceiver() {
    private fun getSavedPreset(context: Context): Preset {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        val savedId = sharedPrefs.getString("preset_id", "mask")!!
        return Presets.find { it.id == savedId } ?: Presets[0]
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != AudioEffect.ACTION_OPEN_AUDIO_EFFECT_CONTROL_SESSION)
            return

        val sessionId = intent.getIntExtra(Equalizer.EXTRA_AUDIO_SESSION, -1)
        if (sessionId == -1)
            return

        val savedPreset = getSavedPreset(context)
        Eeku(sessionId, savedPreset).also {
            it.enable()
            Toast.makeText(context, "Setup Eeku (${savedPreset.displayName}) sessionId: $sessionId", Toast.LENGTH_SHORT).show()
        }
    }
}