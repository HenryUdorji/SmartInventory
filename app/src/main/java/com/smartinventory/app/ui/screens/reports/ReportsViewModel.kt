package com.smartinventory.app.ui.screens.reports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartinventory.app.data.model.ChartDataPoint
import com.smartinventory.app.data.model.Item
import com.smartinventory.app.data.model.LowStockItem
import com.smartinventory.app.data.repository.ItemRepository
import com.smartinventory.app.utils.NetworkMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val itemRepository: ItemRepository,
    private val networkMonitor: NetworkMonitor
) : ViewModel() {

    val isOnline = networkMonitor.isOnline.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = true
    )

    val reportState = combine(
        itemRepository.getAllItems(),
        itemRepository.getQuantityByCategory(),
    ) { items, categories ->
        ReportsUiState(
            isLoading = false,
            categoryData = categories,
            lowStockItems = findLowStockItems(items),
            valueDistributionData = processValueDistribution(items),
            totalInventoryValue = calculateTotalInventoryValue(items)
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ReportsUiState()
    )

    private fun findLowStockItems(items: List<Item>): List<LowStockItem> {
        // Define low stock threshold (for example, less than 5 items)
        val lowStockThreshold = 5

        return items.filter { it.quantity < lowStockThreshold }
            .map { LowStockItem(it.name, it.quantity) }
            .sortedBy { it.quantity }
    }

    private fun processValueDistribution(items: List<Item>): List<ChartDataPoint> {
        // Calculate value (price * quantity) for each category
        val categoryValues = items.groupBy { it.category }
            .mapValues { (_, items) -> items.sumOf { it.price * it.quantity } }
            .toList()
            .sortedByDescending { (_, value) -> value }
            .take(7) // Limit to top 7 categories

        return categoryValues.map { (category, value) ->
            ChartDataPoint(
                label = category,
                value = value.toFloat()
            )
        }
    }

    private fun calculateTotalInventoryValue(items: List<Item>): Double {
        return items.sumOf { it.price * it.quantity }
    }
}