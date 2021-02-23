package com.draco.eeku.views

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.RadioGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.draco.eeku.R
import com.draco.eeku.adapters.SelectRecyclerAdapter
import com.draco.eeku.repositories.Presets
import com.draco.eeku.utils.PresetChartModelFactory
import com.draco.eeku.viewmodels.ConfigActivityViewModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.google.android.material.switchmaterial.SwitchMaterial

class ConfigActivity : AppCompatActivity() {
    private val viewModel: ConfigActivityViewModel by viewModels()

    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var enabledSwitch: SwitchMaterial
    private lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)

        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        enabledSwitch = findViewById(R.id.enabled)
        recycler = findViewById(R.id.recycler)

        /*radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val presetId = Presets[checkedId].id
            viewModel.savePresetId(presetId)
        }*/

        //viewModel.selectSavedPresetRadioButton(radioGroup)
        viewModel.updateEnabledSwitch(enabledSwitch)

        enabledSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.saveEnabled(isChecked)
        }

        recycler.apply {
            adapter = SelectRecyclerAdapter()
            layoutManager = LinearLayoutManager(this@ConfigActivity)
        }
    }
}