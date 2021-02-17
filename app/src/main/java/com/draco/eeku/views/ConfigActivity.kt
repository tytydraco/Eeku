package com.draco.eeku.views

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.RadioGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.draco.eeku.R
import com.draco.eeku.repositories.Presets
import com.draco.eeku.utils.PresetChartModelFactory
import com.draco.eeku.viewmodels.ConfigActivityViewModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView

class ConfigActivity : AppCompatActivity() {
    private val viewModel: ConfigActivityViewModel by viewModels()

    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var radioGroup: RadioGroup
    private lateinit var chart: AAChartView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)

        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        radioGroup = findViewById(R.id.radio_group)
        chart = findViewById(R.id.chart)

        viewModel.populateRadioGroup(this, radioGroup)

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val presetId = Presets[checkedId].id
            viewModel.savePresetId(presetId)

            updateChart()
        }

        updateChart()

        viewModel.selectSavedPresetRadioButton(radioGroup)
    }

    private fun updateChart() {
        val savedPreset = viewModel.getSavedPreset()
        val chartModel = PresetChartModelFactory(savedPreset).create()
        chart.aa_drawChartWithChartModel(chartModel)
    }
}