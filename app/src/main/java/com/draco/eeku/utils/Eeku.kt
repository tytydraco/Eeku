package com.draco.eeku.utils

import android.media.audiofx.DynamicsProcessing
import android.util.Log
import com.draco.eeku.models.Preset

class Eeku(sessionId: Int, preset: Preset) {
    private val processing = DynamicsProcessing(sessionId)
    private val equalizer = DynamicsProcessing.Eq(
        true,
        true,
        preset.map.size
    )

    private fun setupBandsForMap(map: Map<Float, Float>) {
        map.toList().forEachIndexed { index, pair ->
            val eqBand = DynamicsProcessing.EqBand(true, pair.first, pair.second)
            equalizer.setBand(index, eqBand)
        }

        processing.setPostEqAllChannelsTo(equalizer)
    }

    fun enable() {
        processing.enabled = true
    }

    fun disable() {
        processing.enabled = false
    }

    init {
        setupBandsForMap(preset.map)
    }
}