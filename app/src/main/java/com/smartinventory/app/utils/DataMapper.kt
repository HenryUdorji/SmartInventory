package com.smartinventory.app.utils

import com.smartinventory.app.data.local.entity.ItemEntity
import com.smartinventory.app.data.model.Item
import com.smartinventory.app.data.remote.dto.ProductDto
import java.util.Date

/**
 * @Author: ifechukwu.udorji
 * @Date: 4/9/2025
 */
object DataMapper {

    fun mapProductDtoToItem(productDto: ProductDto): Item {
        return Item(
            id = productDto.id,
            name = productDto.title,
            description = productDto.description,
            price = productDto.price,
            quantity = productDto.stock,
            category = productDto.category,
            imageUrl = productDto.thumbnail,
            supplierInfo = productDto.brand,
            lastUpdated = Date()
        )
    }

    fun mapItemToEntity(item: Item): ItemEntity {
        return ItemEntity(
            id = item.id,
            name = item.name,
            description = item.description,
            price = item.price,
            quantity = item.quantity,
            category = item.category,
            imageUrl = item.imageUrl,
            supplierInfo = item.supplierInfo ?: "",
            lastUpdated = item.lastUpdated.time
        )
    }

    fun mapEntityToItem(entity: ItemEntity): Item {
        return Item(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            price = entity.price,
            quantity = entity.quantity,
            category = entity.category,
            imageUrl = entity.imageUrl,
            supplierInfo = entity.supplierInfo,
            lastUpdated = Date(entity.lastUpdated)
        )
    }
}