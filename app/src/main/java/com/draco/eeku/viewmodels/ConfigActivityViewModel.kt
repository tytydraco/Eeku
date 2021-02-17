package com.draco.eeku.viewmodels

import android.app.Activity
import android.app.Application
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.lifecycle.AndroidViewModel
import androidx.preference.PreferenceManager
import com.draco.eeku.R
import com.draco.eeku.models.Preset
import com.draco.eeku.repositories.Presets
import com.draco.eeku.utils.PresetChartModelFactory
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView

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

    fun savePresetId(id: String) {
        sharedPrefs.edit().also {
            it.putString(context.getString(R.string.pref_key_preset_id), id)
            it.apply()
        }
    }

    fun selectSavedPresetRadioButton(radioGroup: RadioGroup) {
        val savedPresetIndex = Presets.indexOf(getSavedPreset())
        (radioGroup.getChildAt(savedPresetIndex) as RadioButton).isChecked = true
    }

    fun populateRadioGroup(activity: Activity, radioGroup: RadioGroup) {
        for (index in Presets.indices) {
            val preset = Presets[index]
            val radioButton = RadioButton(activity).also {
                it.id = index
                it.text = preset.displayName
            }
            radioGroup.addView(radioButton)
        }
    }

    fun updateChart(chart: AAChartView) {
        val savedPreset = getSavedPreset()
        val chartModel = PresetChartModelFactory(savedPreset).create()
        chart.aa_drawChartWithChartModel(chartModel)
    }
}