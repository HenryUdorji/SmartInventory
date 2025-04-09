package com.smartinventory.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.smartinventory.app.data.local.dao.ActivityLogDao
import com.smartinventory.app.data.local.dao.ItemDao
import com.smartinventory.app.data.local.entity.ActivityLogEntity
import com.smartinventory.app.data.local.entity.ItemEntity

/**
 * @Author: ifechukwu.udorji
 * @Date: 4/9/2025
 */
@Database(
    entities = [ItemEntity::class, ActivityLogEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
    abstract fun activityLogDao(): ActivityLogDao
}