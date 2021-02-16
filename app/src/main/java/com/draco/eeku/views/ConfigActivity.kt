package com.draco.eeku.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.draco.eeku.R
import com.draco.eeku.fragments.ConfigPreferenceFragment

class ConfigActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, ConfigPreferenceFragment())
            .commit()
    }
}