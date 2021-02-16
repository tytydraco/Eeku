package com.draco.eeku.models

data class Preset(
    val id: String,
    val displayName: String,
    val map: Map<Int, Float>
)