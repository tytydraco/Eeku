package com.draco.eeku.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.draco.eeku.adapters.SelectRecyclerAdapter

class ConfigActivityViewModel(application: Application) : AndroidViewModel(application) {
    fun setupRecycler(recycler: RecyclerView) {
        recycler.apply {
            adapter = SelectRecyclerAdapter(context)
            layoutManager = LinearLayoutManager(context)
        }
    }
}