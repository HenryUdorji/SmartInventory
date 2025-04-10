package com.smartinventory.app.ui.screens.itemdetail

import com.smartinventory.app.data.model.Item

/**
 * @Author: ifechukwu.udorji
 * @Date: 4/10/2025
 */
sealed class ItemDetailState {
    data object Loading : ItemDetailState()
    data class Success(val item: Item) : ItemDetailState()
    data class Error(val message: String) : ItemDetailState()
}