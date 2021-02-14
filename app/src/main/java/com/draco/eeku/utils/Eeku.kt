package com.draco.eeku.utils

import android.media.audiofx.DynamicsProcessing
import android.util.Log
import com.draco.eeku.models.PresetEqualizerMaps

class Eeku(sessionId: Int, map: Map<Float, Float>) {
    private val processing = DynamicsProcessing(sessionId)
    private val equalizer = DynamicsProcessing.Eq(
        true,
        true,
        PresetEqualizerMaps.MASK.size
    )

    private fun setupBandsForMap(map: Map<Float, Float>) {
        map.toList().forEachIndexed { index, pair ->
            val eqBand = DynamicsProcessing.EqBand(true, pair.first, pair.second)
            equalizer.setBand(index, eqBand)
        }
    }

    init {
        Log.d("Initialized", "EQ")

        setupBandsForMap(PresetEqualizerMaps.MASK)
        setupBandsForMap(map)

        processing.setPostEqAllChannelsTo(equalizer)
        processing.enabled = true
    }
}