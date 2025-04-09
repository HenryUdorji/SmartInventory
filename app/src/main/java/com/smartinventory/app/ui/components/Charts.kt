package com.smartinventory.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.axis.AxisData
import co.yml.charts.axis.DataCategoryOptions
import co.yml.charts.common.model.PlotType
import co.yml.charts.common.model.Point
import co.yml.charts.common.utils.DataUtils
import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.models.BarChartData
import co.yml.charts.ui.barchart.models.BarChartType
import co.yml.charts.ui.barchart.models.BarData
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.smartinventory.app.data.model.CategoryQuantity
import kotlin.math.absoluteValue

/**
 * @Author: ifechukwu.udorji
 * @Date: 4/9/2025
 */
@Composable
fun CategoryBarChart(categoryData: List<CategoryQuantity>) {
    val maxValue = categoryData.maxOfOrNull { it.totalQuantity }?.toFloat() ?: 0f

    val barData = DataUtils.getBarChartData(
        listSize = categoryData.size,
        maxRange = maxValue.toInt(),
        barChartType = BarChartType.VERTICAL,
        dataCategoryOptions = DataCategoryOptions()
    )

    val xAxisData = AxisData.Builder()
        .axisStepSize(30.dp)
        .steps(categoryData.size)
        .labelData { i -> barData.getOrNull(i)?.label ?: "" }
        .labelAndAxisLinePadding(4.dp)
        .axisLineColor(MaterialTheme.colorScheme.onBackground)
        .axisLabelColor(MaterialTheme.colorScheme.onBackground)
        .build()

    val yAxisData = AxisData.Builder()
        .steps(5)
        .labelAndAxisLinePadding(10.dp)
        .labelData { i -> ((maxValue / 5) * i).toInt().toString() }
        .axisLineColor(MaterialTheme.colorScheme.onBackground)
        .axisLabelColor(MaterialTheme.colorScheme.onBackground)
        .build()

    val barChartData = BarChartData(
        chartData = barData,
        yAxisData = yAxisData,
        xAxisData = xAxisData,
        backgroundColor = MaterialTheme.colorScheme.background
    )

    BarChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .padding(16.dp),
        barChartData = barChartData
    )
}


@Composable
fun StockPieChart(inStock: Int, lowStock: Int, outOfStock: Int) {
    val pieChartData = PieChartData(
        slices = listOf(
            PieChartData.Slice("In Stock", inStock.toFloat(), Color(0xFF4CAF50)),
            PieChartData.Slice("Low Stock", lowStock.toFloat(), Color(0xFFFF9800)),
            PieChartData.Slice("Out of Stock", outOfStock.toFloat(), Color(0xFFF44336))
        ),
        plotType = PlotType.Pie
    )

    val pieChartConfig = PieChartConfig(
        showSliceLabels = true,
        labelVisible = true,
        labelType = PieChartConfig.LabelType.PERCENTAGE,
        isAnimationEnable = true,
        chartPadding = 20,
        labelFontSize = 14.sp,
        backgroundColor = MaterialTheme.colorScheme.background
    )

    PieChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(16.dp),
        pieChartData = pieChartData,
        pieChartConfig = pieChartConfig
    )
}

// Helper function to generate colors for categories
private fun generateColorForCategory(category: String): Color {
    val colors = listOf(
        Color(0xFF2196F3), // Blue
        Color(0xFFF44336), // Red
        Color(0xFF4CAF50), // Green
        Color(0xFFFF9800), // Orange
        Color(0xFF9C27B0), // Purple
        Color(0xFF607D8B), // Blue Grey
        Color(0xFFE91E63), // Pink
        Color(0xFF009688), // Teal
        Color(0xFFFFEB3B), // Yellow
        Color(0xFF795548)  // Brown
    )

    // Generate a deterministic color based on the category string
    val index = category.hashCode().absoluteValue % colors.size
    return colors[index]
}