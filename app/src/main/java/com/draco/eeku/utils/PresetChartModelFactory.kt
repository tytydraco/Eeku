package com.draco.eeku.utils

import android.graphics.Color
import com.draco.eeku.models.Preset
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartZoomType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AADataLabels

class PresetChartModelFactory(private val preset: Preset) {
    private val chartModel = AAChartModel()
        .chartType(AAChartType.Spline)
        .dataLabelsEnabled(true)
        .series(getSeries())
        .categories(getCategories())
        .yAxisVisible(false)
        .xAxisVisible(false)
        .backgroundColor(Color.TRANSPARENT)
        .yAxisGridLineWidth(0f)
        .tooltipEnabled(false)

    private fun getSeries() = arrayOf(
        AASeriesElement()
            .name("Gain")
            .showInLegend(false)
            .dataLabels(AADataLabels().enabled(false))
            .data(preset.map.values.toTypedArray())
            .color("#ffff00")
    )

    private fun getCategories() = preset.map.keys.map { formatFrequency(it) }.toTypedArray()

    private fun formatFrequency(freq: Int) = "$freq Hz"

    fun create() = chartModel
}