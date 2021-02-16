package com.draco.eeku.repositories

object FrequencyGainMaps {
    val FLAT = mapOf(
        Frequencies.SUB_BASS to         0.00f,
        Frequencies.BASS to             0.00f,
        Frequencies.LOW_MID to          0.00f,
        Frequencies.MID to              0.00f,
        Frequencies.UPPER_MID to        0.00f,
        Frequencies.PRESENCE to         0.00f,
        Frequencies.BRILLIANCE to       0.00f
    )

    val V_SHAPE = mapOf(
        Frequencies.SUB_BASS to         10.00f,
        Frequencies.BASS to             5.00f,
        Frequencies.LOW_MID to          -5.00f,
        Frequencies.MID to              0.00f,
        Frequencies.UPPER_MID to        0.00f,
        Frequencies.PRESENCE to         5.00f,
        Frequencies.BRILLIANCE to       10.00f
    )
}