package com.smartinventory.app.data.remote.dto

import androidx.annotation.Keep

@Keep
data class ProductResponseDto(
    val limit: Int,
    val products: List<ProductDto>,
    val skip: Int,
    val total: Int
)

@Keep
data class ProductDto(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val brand: String,
    val category: String,
    val thumbnail: String,
    val images: List<String>
)