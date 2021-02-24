package com.draco.eeku.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.draco.eeku.R
import com.draco.eeku.adapters.SelectRecyclerAdapter
import com.draco.eeku.models.Preset
import com.draco.eeku.repositories.Presets

class ConfigActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val context = application.applicationContext
    private val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)

    fun setupRecycler(recycler: RecyclerView) {
        recycler.apply {
            adapter = SelectRecyclerAdapter(context)
            layoutManager = LinearLayoutManager(context)
        }
    }
}