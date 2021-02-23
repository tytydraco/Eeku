package com.draco.eeku.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.draco.eeku.R
import com.draco.eeku.repositories.Presets
import com.draco.eeku.utils.PresetChartModelFactory
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView

class SelectRecyclerAdapter : RecyclerView.Adapter<SelectRecyclerAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.title)!!
        val chart = itemView.findViewById<AAChartView>(R.id.chart)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.select_recycler_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val preset = Presets[position]
        val model = PresetChartModelFactory(preset).create()

        holder.title.text = preset.displayName
        holder.chart.aa_drawChartWithChartModel(model)
    }

    override fun getItemCount(): Int {
        return Presets.size
    }
}