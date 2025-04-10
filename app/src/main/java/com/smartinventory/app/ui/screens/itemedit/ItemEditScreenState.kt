package com.smartinventory.app.ui.screens.itemedit

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * @Author: ifechukwu.udorji
 * @Date: 4/10/2025
 */
data class ItemEditUiState(
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val isSaveSuccessful: Boolean = false,
    val errorMessage: String? = null,
    val id: Int = -1,
    val name: String = "",
    val nameError: String? = null,
    val description: String = "",
    val descriptionError: String? = null,
    val price: String = "",
    val priceError: String? = null,
    val quantity: String = "",
    val quantityError: String? = null,
    val category: String = "",
    val categoryError: String? = null,
    val imageUrl: String = "",
    val imageUrlError: String? = null,
    val supplierName: String = "",
    val supplierNameError: String? = null,
    val supplierContact: String = "",
    val supplierContactError: String? = null,
    val lastUpdated: Date = Date()
) {
    val isValid: Boolean
        get() = nameError == null && descriptionError == null &&
                priceError == null && quantityError == null &&
                categoryError == null && name.isNotBlank() &&
                price.isNotBlank() && quantity.isNotBlank()
}