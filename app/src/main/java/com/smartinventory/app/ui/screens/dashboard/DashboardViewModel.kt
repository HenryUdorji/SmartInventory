package com.smartinventory.app.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartinventory.app.data.repository.ItemRepository
import com.smartinventory.app.utils.NetworkMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author: ifechukwu.udorji
 * @Date: 4/9/2025
 */
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val itemRepository: ItemRepository,
    networkMonitor: NetworkMonitor
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    val isOnline = networkMonitor.isOnline.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = true
    )

    val dashboardState = combine(
        itemRepository.getTotalItemCount(),
        itemRepository.getOutOfStockCount(),
        itemRepository.getRecentActivity(5),
        itemRepository.getQuantityByCategory()
    ) { totalItems, outOfStock, recentActivity, categoryData ->
        DashboardState(
            totalItems = totalItems,
            outOfStockItems = outOfStock,
            lowStockItems = categoryData
                .filter { it.totalQuantity in 1..5 }
                .sumOf { it.totalQuantity },
            recentActivity = recentActivity,
            categoryStockData = categoryData
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DashboardState()
    )

    init {
        refreshData()
    }

    fun refreshData() {
        viewModelScope.launch {
            _isLoading.value = true
            itemRepository.refreshItems()
            _isLoading.value = false
        }
    }
}
