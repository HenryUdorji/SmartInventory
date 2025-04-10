package com.smartinventory.app.ui.screens.itemlist

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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author: ifechukwu.udorji
 * @Date: 4/10/2025
 */
@HiltViewModel
class ItemListViewModel @Inject constructor(
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

    val items = itemRepository.getAllItems()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val categoriesWithCounts = items.map { itemList ->
        val allCategories = mutableSetOf("All")
        allCategories.addAll(itemList.map { it.category }.distinct())

        val countMap = allCategories.associateWith { category ->
            if (category == "All") {
                itemList.size
            } else {
                itemList.count { it.category == category }
            }
        }

        countMap
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = mapOf("All" to 0)
    )

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    val filteredItems = combine(selectedCategory, items) { category, itemsList ->
        if (category == "All") {
            itemsList
        } else {
            itemsList.filter { it.category == category }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    init {
        refreshItems()
    }

    fun refreshItems(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _isLoading.value = true
            itemRepository.fetchItems(forceRefresh)
            _isLoading.value = false
        }
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = category
    }
}