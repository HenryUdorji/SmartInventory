package com.smartinventory.app.ui.screens.itemdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartinventory.app.data.repository.ItemRepository
import com.smartinventory.app.utils.NetworkMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author: ifechukwu.udorji
 * @Date: 4/10/2025
 */
@HiltViewModel
class ItemDetailViewModel @Inject constructor(
    private val itemRepository: ItemRepository,
    private val networkMonitor: NetworkMonitor
) : ViewModel() {

    private val _itemState = MutableStateFlow<ItemDetailState>(ItemDetailState.Loading)
    val itemState: StateFlow<ItemDetailState> = _itemState.asStateFlow()

    private var currentItemId: Int = -1

    val isOnline = networkMonitor.isOnline.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = true
    )

    fun loadItem(itemId: Int) {
        currentItemId = itemId
        viewModelScope.launch {
            _itemState.value = ItemDetailState.Loading
            try {
                val item = itemRepository.getItemById(itemId)
                if (item != null) {
                    _itemState.value = ItemDetailState.Success(item)
                } else {
                    _itemState.value = ItemDetailState.Error("Item not found")
                }
            } catch (e: Exception) {
                _itemState.value = ItemDetailState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun deleteItem() {
        viewModelScope.launch {
            try {
                val currentState = _itemState.value
                if (currentState is ItemDetailState.Success) {
                    itemRepository.deleteItem(currentState.item)
                }
            } catch (e: Exception) {
                // Handle error, possibly show a message
            }
        }
    }
}