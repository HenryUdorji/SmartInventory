package com.smartinventory.app.data.repository

import com.smartinventory.app.data.local.dao.ActivityLogDao
import com.smartinventory.app.data.local.dao.ItemDao
import com.smartinventory.app.data.local.entity.ActivityLogEntity
import com.smartinventory.app.data.model.CategoryQuantity
import com.smartinventory.app.data.model.Item
import com.smartinventory.app.data.remote.api.ProductApi
import com.smartinventory.app.utils.DataMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

/**
 * @Author: ifechukwu.udorji
 * @Date: 4/9/2025
 */

class ItemRepositoryImpl @Inject constructor(
    private val itemDao: ItemDao,
    private val activityLogDao: ActivityLogDao,
    private val productApi: ProductApi,
    private val ioDispatcher: CoroutineDispatcher
) : ItemRepository {

    override fun getAllItems(): Flow<List<Item>> {
        return itemDao.getAllItems().map { entities ->
            entities.map { DataMapper.mapEntityToItem(it) }
        }
    }

    override suspend fun getItemById(id: Int): Item? = withContext(ioDispatcher) {
        itemDao.getItemById(id)?.let { DataMapper.mapEntityToItem(it) }
    }

    override suspend fun addItem(item: Item) = withContext(ioDispatcher) {
        val entity = DataMapper.mapItemToEntity(item)
        itemDao.insertItem(entity)
        logActivity(item.id, "CREATE", "Added new item: ${item.name}")
    }

    override suspend fun updateItem(item: Item) = withContext(ioDispatcher) {
        val entity = DataMapper.mapItemToEntity(item)
        itemDao.updateItem(entity)
        logActivity(item.id, "UPDATE", "Updated item: ${item.name}")
    }

    override suspend fun deleteItem(item: Item) = withContext(ioDispatcher) {
        val entity = DataMapper.mapItemToEntity(item)
        itemDao.deleteItem(entity)
        logActivity(item.id, "DELETE", "Deleted item: ${item.name}")
    }

    override suspend fun fetchItems(forceRefresh: Boolean) = withContext(ioDispatcher) {
        try {
            if (!forceRefresh && itemDao.getItemCount() > 0) {
                return@withContext // Use cached data if not forcing refresh
            }

            val response = productApi.getAllProducts()
            val items = response.products.map { DataMapper.mapProductDtoToItem(it) }
            val entities = items.map { DataMapper.mapItemToEntity(it) }
            itemDao.insertItems(entities)
        } catch (e: Exception) {
            // Just use cached data if refresh fails
            e.printStackTrace()
        }
    }

    override fun getTotalItemCount(): Flow<Int> = itemDao.getTotalItemCount()

    override fun getOutOfStockCount(): Flow<Int> = itemDao.getOutOfStockCount()

    override fun getAllCategories(): Flow<List<String>> = itemDao.getAllCategories()

    override fun getQuantityByCategory(): Flow<List<CategoryQuantity>> = itemDao.getQuantityByCategory()

    override fun getRecentActivity(limit: Int): Flow<List<String>> {
        return activityLogDao.getRecentLogs(limit).map { logs ->
            logs.map { it.details }
        }
    }

    private suspend fun logActivity(itemId: Int, action: String, details: String) {
        activityLogDao.insertLog(
            ActivityLogEntity(
                itemId = itemId,
                action = action,
                timestamp = Date().time,
                details = details
            )
        )
    }
}