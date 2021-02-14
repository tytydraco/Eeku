package com.draco.eeku.utils

import android.content.Context
import androidx.preference.PreferenceManager
import com.draco.eeku.models.PresetEqualizerMaps

object PresetResolver {
    fun resolvePresetByName(name: String): Map<Float, Float> {
        return when (name) {
            "less_bright" ->    PresetEqualizerMaps.LESS_BRIGHT
            else ->             PresetEqualizerMaps.MASK
        }
    }

    fun resolveNameByPreset(preset: Map<Float, Float>): String {
        return when (preset) {
            PresetEqualizerMaps.LESS_BRIGHT ->      "less_bright"
            else ->                                 "default"
        }
    }

    fun getSavedPreset(context: Context): Map<Float, Float> {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        val name = sharedPrefs.getString("preset", "default")!!
        return resolvePresetByName(name)
    }

    fun putSavedPreset(context: Context, preset: Map<Float, Float>) {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        with (sharedPrefs.edit()) {
            putString("preset", resolveNameByPreset(preset))
            apply()
        }
    }
}