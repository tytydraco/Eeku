package com.draco.eeku.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.draco.eeku.R
import com.draco.eeku.repositories.Presets
import com.google.android.material.card.MaterialCardView

class SelectRecyclerAdapter(private val context: Context) : RecyclerView.Adapter<SelectRecyclerAdapter.ViewHolder>() {
    private val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card = itemView.findViewById<MaterialCardView>(R.id.card)!!
        val title = itemView.findViewById<TextView>(R.id.title)!!
        val description = itemView.findViewById<TextView>(R.id.description)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.select_recycler_item, parent, false)
        return ViewHolder(view)
    }

    private fun savePresetId(id: String) {
        sharedPrefs.edit().also {
            it.putString(context.getString(R.string.pref_key_preset_id), id)
            it.apply()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val preset = Presets[position]

        holder.card.setCardBackgroundColor(Color.parseColor(preset.color))
        holder.title.text = preset.title
        holder.description.text = preset.description

        holder.card.setOnClickListener {
            val anim = AnimationUtils.loadAnimation(context, R.anim.pop)
            it.startAnimation(anim)
            savePresetId(preset.id)
        }
    }

    override fun getItemCount(): Int {
        return Presets.size
    }
}