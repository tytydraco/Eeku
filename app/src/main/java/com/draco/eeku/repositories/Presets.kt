package com.draco.eeku.repositories

import com.draco.eeku.models.Preset

val Presets = listOf(
    Preset(
        "mask",
        "Flat",
        FrequencyGainMaps.FLAT
    ),
    Preset(
        "less_bright",
        "Less Bright",
        FrequencyGainMaps.LESS_BRIGHT
    )
)