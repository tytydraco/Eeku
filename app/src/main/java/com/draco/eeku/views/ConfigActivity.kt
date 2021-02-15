package com.draco.eeku.views

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
import com.draco.eeku.viewmodels.ConfigActivityViewModel

class ConfigActivity : AppCompatActivity() {
    //TODO: Clean up, make pretty, use VM
    private val viewModel: ConfigActivityViewModel by viewModels()

    private lateinit var spinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)

        spinner = findViewById(R.id.spinner)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, Presets.map { it.displayName })

        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                PreferenceManager.getDefaultSharedPreferences(this@ConfigActivity).edit().apply {
                    putString("preset_id", Presets[position].id)
                    apply()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val savedId = PreferenceManager.getDefaultSharedPreferences(this@ConfigActivity).getString("preset_id", "mask")!!
        val savedPreset = Presets.find { it.id == savedId }
        val savedPresetIndex = Presets.indexOf(savedPreset)
        spinner.setSelection(savedPresetIndex)
    }
}