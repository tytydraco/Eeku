package com.draco.eeku.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.preference.PreferenceManager
import com.draco.eeku.R
import com.draco.eeku.models.Preset
import com.draco.eeku.repositories.Presets

class ConfigActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val context = application.applicationContext
    private val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)

    fun getSavedPresetId(): String {
        return sharedPrefs.getString(context.getString(R.string.pref_key_preset_id), null) ?: Presets[0].id
    }

    fun getSavedPreset(): Preset {
        val savedId = getSavedPresetId()
        return Presets.find { it.id == savedId } ?: Presets[0]
    }
}