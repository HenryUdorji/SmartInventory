package com.smartinventory.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.smartinventory.app.data.local.entity.ActivityLogEntity
import kotlinx.coroutines.flow.Flow

/**
 * @Author: ifechukwu.udorji
 * @Date: 4/9/2025
 */
@Dao
interface ActivityLogDao {
    @Insert
    suspend fun insertLog(log: ActivityLogEntity)

    @Query("SELECT * FROM activity_logs ORDER BY timestamp DESC LIMIT :limit")
    fun getRecentLogs(limit: Int = 10): Flow<List<ActivityLogEntity>>
}