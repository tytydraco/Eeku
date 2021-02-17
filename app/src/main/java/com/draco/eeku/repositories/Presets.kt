package com.draco.eeku.repositories

import com.draco.eeku.models.Preset

val Presets = listOf(
    Preset(
        "mask",
        "Flat",
        mapOf(
            Frequencies.SUB_BASS to         0.00f,
            Frequencies.BASS to             0.00f,
            Frequencies.LOW_MID to          0.00f,
            Frequencies.MID to              0.00f,
            Frequencies.UPPER_MID to        0.00f,
            Frequencies.PRESENCE to         0.00f,
            Frequencies.BRILLIANCE to       0.00f
        )
    ),
    Preset(
        "v_shape",
        "V Shaped",
        mapOf(
            Frequencies.SUB_BASS to         10.00f,
            Frequencies.BASS to             5.00f,
            Frequencies.LOW_MID to          -5.00f,
            Frequencies.MID to              0.00f,
            Frequencies.UPPER_MID to        0.00f,
            Frequencies.PRESENCE to         5.00f,
            Frequencies.BRILLIANCE to       10.00f
        )
    ),
    Preset(
        "w_shape",
        "W Shaped",
        mapOf(
            Frequencies.SUB_BASS to         10.00f,
            Frequencies.BASS to             5.00f,
            Frequencies.LOW_MID to          -5.00f,
            Frequencies.MID to              5.00f,
            Frequencies.UPPER_MID to        0.00f,
            Frequencies.PRESENCE to         5.00f,
            Frequencies.BRILLIANCE to       10.00f
        )
    )
)