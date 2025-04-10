package com.smartinventory.app.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

/**
 * @Author: ifechukwu.udorji
 * @Date: 4/9/2025
 */
@Parcelize
data class Item(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val quantity: Int,
    val category: String,
    val imageUrl: String,
    val supplierInfo: String? = "",
    val supplierName: String? = "",
    val supplierContact: String? = "",
    val lastUpdated: Date = Date()
) : Parcelable