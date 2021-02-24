package com.draco.eeku.repositories

import android.graphics.Color
import com.draco.eeku.models.Preset

val Presets = listOf(
    Preset(
        "mask",
        "Flat",
        "No adjustments",
        "#78909c",
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
        "Elevated lows and highs with recessed mids",
        "#ff5252",
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
        "Elevated lows, highs, and vocals",
        "#2196f3",
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