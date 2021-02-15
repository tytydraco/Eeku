package com.draco.eeku.models

import com.draco.eeku.utils.DecibelUtils

object FrequencyGainMaps {
    val FLAT = mapOf(
        Frequencies.SUB_BASS to         DecibelUtils.loudnessToDb(1.0f),
        Frequencies.BASS to             DecibelUtils.loudnessToDb(1.0f),
        Frequencies.LOW_MID to          DecibelUtils.loudnessToDb(1.0f),
        Frequencies.MID to              DecibelUtils.loudnessToDb(1.0f),
        Frequencies.UPPER_MID to        DecibelUtils.loudnessToDb(1.0f),
        Frequencies.PRESENCE to         DecibelUtils.loudnessToDb(1.0f),
        Frequencies.BRILLIANCE to       DecibelUtils.loudnessToDb(1.0f)
    )

    val LESS_BRIGHT = mapOf(
        Frequencies.SUB_BASS to         DecibelUtils.loudnessToDb(1.0f),
        Frequencies.BASS to             DecibelUtils.loudnessToDb(1.0f),
        Frequencies.LOW_MID to          DecibelUtils.loudnessToDb(1.0f),
        Frequencies.MID to              DecibelUtils.loudnessToDb(1.0f),
        Frequencies.UPPER_MID to        DecibelUtils.loudnessToDb(0.9f),
        Frequencies.PRESENCE to         DecibelUtils.loudnessToDb(0.8f),
        Frequencies.BRILLIANCE to       DecibelUtils.loudnessToDb(1.0f)
    )
}