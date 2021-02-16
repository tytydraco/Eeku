package com.draco.eeku.repositories

import com.draco.eeku.models.Preset

val Presets = listOf(
    Preset(
        "mask",
        "Flat",
        FrequencyGainMaps.FLAT
    ),
    Preset(
        "v_shape",
        "V Shaped",
        FrequencyGainMaps.V_SHAPE
    ),
    Preset(
        "punchy",
        "Punchy",
        FrequencyGainMaps.PUNCHY
    )
)