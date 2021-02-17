package com.draco.eeku.views

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.draco.eeku.R
import com.draco.eeku.repositories.Presets
import com.draco.eeku.utils.PresetChartModelFactory
import com.draco.eeku.viewmodels.ConfigActivityViewModel
import com.github.aachartmodel.aainfographics.aachartcreator.*
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AADataLabels

class ConfigActivity : AppCompatActivity() {
    private val viewModel: ConfigActivityViewModel by viewModels()

    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var spinner: Spinner
    private lateinit var chart: AAChartView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)

        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        spinner = findViewById(R.id.spinner)
        chart = findViewById(R.id.chart)

        val spinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            Presets.map { it.displayName }.toTypedArray()
        )

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                sharedPrefs.edit().also {
                    it.putString(getString(R.string.pref_key_preset_id), Presets[position].id)
                    it.apply()
                }

                updateChart()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spinner.adapter = spinnerAdapter

        val savedPresetIndex = Presets.indexOf(viewModel.getSavedPreset())
        spinner.setSelection(savedPresetIndex)

        updateChart()
    }

    private fun updateChart() {
        val savedPreset = viewModel.getSavedPreset()
        val chartModel = PresetChartModelFactory(savedPreset).create()
        chart.aa_drawChartWithChartModel(chartModel)
    }
}