package com.draco.eeku.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.draco.eeku.R
import com.draco.eeku.viewmodels.ConfigActivityViewModel

class ConfigActivity : AppCompatActivity() {
    private val viewModel: ConfigActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)
    }
}