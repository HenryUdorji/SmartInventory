package com.smartinventory.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.smartinventory.app.data.local.entity.ItemEntity
import com.smartinventory.app.data.model.CategoryQuantity
import kotlinx.coroutines.flow.Flow

/**
 * @Author: ifechukwu.udorji
 * @Date: 4/9/2025
 */
@Dao
interface ItemDao {
    @Query("SELECT * FROM items ORDER BY lastUpdated DESC")
    fun getAllItems(): Flow<List<ItemEntity>>

    @Query("SELECT * FROM items WHERE id = :itemId")
    suspend fun getItemById(itemId: Int): ItemEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ItemEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<ItemEntity>)

    @Update
    suspend fun updateItem(item: ItemEntity)

    @Delete
    suspend fun deleteItem(item: ItemEntity)

    @Query("SELECT COUNT(*) FROM items")
    fun getTotalItemCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM items WHERE quantity <= 0")
    fun getOutOfStockCount(): Flow<Int>

    @Query("SELECT DISTINCT category FROM items")
    fun getAllCategories(): Flow<List<String>>

    @Query("SELECT SUM(quantity) as totalQuantity, category FROM items GROUP BY category")
    fun getQuantityByCategory(): Flow<List<CategoryQuantity>>
}