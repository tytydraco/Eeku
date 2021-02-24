package com.draco.eeku.models

data class Preset(
    val id: String,
    val title: String,
    val description: String,
    val color: String,
    val map: Map<Int, Float>
)