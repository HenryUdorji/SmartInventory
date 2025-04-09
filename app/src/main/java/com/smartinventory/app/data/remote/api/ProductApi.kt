package com.smartinventory.app.data.remote.api

import com.smartinventory.app.data.remote.dto.ProductDto
import com.smartinventory.app.data.remote.dto.ProductResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @Author: ifechukwu.udorji
 * @Date: 4/9/2025
 */
interface ProductApi {
    @GET("products")
    suspend fun getAllProducts(): ProductResponseDto

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): ProductDto

    @GET("products/categories")
    suspend fun getCategories(): List<String>

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(@Path("category") category: String): ProductResponseDto
}