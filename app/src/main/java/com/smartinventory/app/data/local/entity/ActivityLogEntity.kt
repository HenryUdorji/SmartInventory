package com.smartinventory.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Author: ifechukwu.udorji
 * @Date: 4/9/2025
 */
@Entity(tableName = "activity_logs")
data class ActivityLogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val itemId: Int,
    val action: String, // "CREATE", "UPDATE", "DELETE"
    val timestamp: Long,
    val details: String
)