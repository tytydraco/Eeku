package com.draco.eeku.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.DropDownPreference
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.draco.eeku.R
import com.draco.eeku.repositories.Presets

class ConfigPreferenceFragment : PreferenceFragmentCompat() {
    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var presetDropDown: ListPreference

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        presetDropDown = findPreference(getString(R.string.pref_key_preset_dropdown))!!

        presetDropDown.apply {
            entries = Presets.map { it.displayName }.toTypedArray()
            entryValues = Presets.map { it.id }.toTypedArray()

            setOnPreferenceClickListener {
                sharedPrefs.edit().also {
                    it.putString(getString(R.string.pref_key_preset_id), value)
                    it.apply()
                }

                return@setOnPreferenceClickListener true
            }
        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.config, rootKey)
    }
}