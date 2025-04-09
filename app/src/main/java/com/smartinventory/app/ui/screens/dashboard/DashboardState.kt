package com.smartinventory.app.ui.screens.dashboard

import com.smartinventory.app.data.model.CategoryQuantity

/**
 * @Author: ifechukwu.udorji
 * @Date: 4/9/2025
 */
data class DashboardState(
    val totalItems: Int = 0,
    val outOfStockItems: Int = 0,
    val lowStockItems: Int = 0,
    val recentActivity: List<String> = emptyList(),
    val categoryStockData: List<CategoryQuantity> = emptyList()
)