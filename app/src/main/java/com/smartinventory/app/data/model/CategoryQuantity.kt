package com.smartinventory.app.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @Author: ifechukwu.udorji
 * @Date: 4/9/2025
 */
@Parcelize
data class CategoryQuantity(
    val category: String,
    val totalQuantity: Int
): Parcelable