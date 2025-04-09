package com.smartinventory.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Author: ifechukwu.udorji
 * @Date: 4/9/2025
 */
@Entity(tableName = "items")
data class ItemEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val quantity: Int,
    val category: String,
    val imageUrl: String,
    val supplierInfo: String,
    val lastUpdated: Long
)