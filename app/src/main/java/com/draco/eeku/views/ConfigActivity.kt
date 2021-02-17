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
import com.google.android.material.switchmaterial.SwitchMaterial

class ConfigActivity : AppCompatActivity() {
    private val viewModel: ConfigActivityViewModel by viewModels()

    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var radioGroup: RadioGroup
    private lateinit var chart: AAChartView
    private lateinit var enabledSwitch: SwitchMaterial

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)

        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        radioGroup = findViewById(R.id.radio_group)
        chart = findViewById(R.id.chart)
        enabledSwitch = findViewById(R.id.enabled)

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val presetId = Presets[checkedId].id
            viewModel.savePresetId(presetId)

            viewModel.updateChart(chart)
        }

        viewModel.populateRadioGroup(this, radioGroup)
        viewModel.selectSavedPresetRadioButton(radioGroup)
        viewModel.updateChart(chart)
        viewModel.updateEnabledSwitch(enabledSwitch)

        enabledSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.saveEnabled(isChecked)
        }
    }
}