package com.smartinventory.app.ui.screens.reports

import androidx.compose.ui.graphics.Color
import com.smartinventory.app.data.model.CategoryQuantity
import com.smartinventory.app.data.model.ChartDataPoint
import com.smartinventory.app.data.model.LowStockItem

/**
 * @Author: ifechukwu.udorji
 * @Date: 4/10/2025
 */
data class ReportsUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val categoryData: List<CategoryQuantity> = emptyList(),
    val categoryColors: List<Color> = emptyList(),
    val lowStockItems: List<LowStockItem> = emptyList(),
    val valueDistributionData: List<ChartDataPoint> = emptyList(),
    val totalInventoryValue: Double = 0.0,
)