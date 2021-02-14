package com.draco.eeku.utils

import kotlin.math.log

object DecibelUtils {
    fun loudnessToDb(ratio: Float) = 33.22f * log(ratio, 2f)
}