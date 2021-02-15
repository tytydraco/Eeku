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
import com.draco.eeku.fragments.ConfigPreferenceFragment
import com.draco.eeku.repositories.Presets
import com.draco.eeku.viewmodels.ConfigActivityViewModel

class ConfigActivity : AppCompatActivity() {
    private val viewModel: ConfigActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, ConfigPreferenceFragment())
            .commit()
    }
}