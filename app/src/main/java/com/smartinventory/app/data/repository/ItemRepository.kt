package com.smartinventory.app.data.repository

import com.smartinventory.app.data.local.entity.ActivityLogEntity
import com.smartinventory.app.data.model.CategoryQuantity
import com.smartinventory.app.data.model.Item
import kotlinx.coroutines.flow.Flow

/**
 * @Author: ifechukwu.udorji
 * @Date: 4/9/2025
 */
interface ItemRepository {
    fun getAllItems(): Flow<List<Item>>

    suspend fun getItemById(id: Int): Item?

    suspend fun addItem(item: Item)

    suspend fun updateItem(item: Item)

    suspend fun deleteItem(item: Item)

    suspend fun fetchItems(forceRefresh: Boolean = false)

    fun getTotalItemCount(): Flow<Int>

    fun getOutOfStockCount(): Flow<Int>

    fun getAllCategories(): Flow<List<String>>

    fun getQuantityByCategory(): Flow<List<CategoryQuantity>>

    fun getRecentActivity(limit: Int = 10): Flow<List<String>>
}