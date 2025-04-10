package com.smartinventory.app.ui.screens.itemedit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartinventory.app.data.model.Item
import com.smartinventory.app.data.repository.ItemRepository
import com.smartinventory.app.utils.NetworkMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

/**
 * @Author: ifechukwu.udorji
 * @Date: 4/10/2025
 */
@HiltViewModel
class ItemEditViewModel @Inject constructor(
    private val itemRepository: ItemRepository,
    private val networkMonitor: NetworkMonitor
) : ViewModel() {

    private val _uiState = MutableStateFlow(ItemEditUiState())
    val uiState: StateFlow<ItemEditUiState> = _uiState.asStateFlow()

    val isOnline = networkMonitor.isOnline.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = true
    )

    fun initializeScreen(itemId: Int) {
        if (itemId == -1) {
            // We're adding a new item, no need to load data
            _uiState.update { it.copy(id = -1) }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val item = itemRepository.getItemById(itemId)
                if (item != null) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            id = item.id,
                            name = item.name,
                            description = item.description,
                            price = item.price.toString(),
                            quantity = item.quantity.toString(),
                            category = item.category,
                            imageUrl = item.imageUrl ?: "",
                            supplierName = item.supplierName ?: "",
                            supplierContact = item.supplierContact ?: "",
                            lastUpdated = item.lastUpdated
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Item not found"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Unknown error"
                    )
                }
            }
        }
    }

    fun onNameChange(name: String) {
        _uiState.update {
            it.copy(
                name = name,
                nameError = if (name.isBlank()) "Name is required" else null
            )
        }
    }

    fun onDescriptionChange(description: String) {
        _uiState.update {
            it.copy(
                description = description,
                descriptionError = if (description.isBlank()) "Description is required" else null
            )
        }
    }

    fun onPriceChange(price: String) {
        _uiState.update {
            it.copy(
                price = price,
                priceError = validatePrice(price)
            )
        }
    }

    fun onQuantityChange(quantity: String) {
        _uiState.update {
            it.copy(
                quantity = quantity,
                quantityError = validateQuantity(quantity)
            )
        }
    }

    fun onCategoryChange(category: String) {
        _uiState.update {
            it.copy(
                category = category,
                categoryError = if (category.isBlank()) "Category is required" else null
            )
        }
    }

    fun onImageUrlChange(imageUrl: String) {
        _uiState.update {
            it.copy(imageUrl = imageUrl)
        }
    }

    fun onSupplierNameChange(supplierName: String) {
        _uiState.update {
            it.copy(supplierName = supplierName)
        }
    }

    fun onSupplierContactChange(supplierContact: String) {
        _uiState.update {
            it.copy(supplierContact = supplierContact)
        }
    }

    fun saveItem() {
        val currentState = _uiState.value

        // Validate all fields
        val nameError = if (currentState.name.isBlank()) "Name is required" else null
        val descriptionError = if (currentState.description.isBlank()) "Description is required" else null
        val priceError = validatePrice(currentState.price)
        val quantityError = validateQuantity(currentState.quantity)
        val categoryError = if (currentState.category.isBlank()) "Category is required" else null

        // Update state with validation errors
        _uiState.update {
            it.copy(
                nameError = nameError,
                descriptionError = descriptionError,
                priceError = priceError,
                quantityError = quantityError,
                categoryError = categoryError
            )
        }

        // Check if all validations pass
        if (nameError != null || descriptionError != null ||
            priceError != null || quantityError != null ||
            categoryError != null) {
            return
        }

        // Proceed with saving
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }

            try {
                val priceValue = currentState.price.toDoubleOrNull() ?: 0.0
                val quantityValue = currentState.quantity.toIntOrNull() ?: 0

                val item = Item(
                    id = currentState.id,
                    name = currentState.name,
                    description = currentState.description,
                    price = priceValue,
                    quantity = quantityValue,
                    category = currentState.category,
                    imageUrl = currentState.imageUrl.takeIf { it.isNotBlank() } ?: "",
                    supplierName = currentState.supplierName.takeIf { it.isNotBlank() },
                    supplierContact = currentState.supplierContact.takeIf { it.isNotBlank() },
                    lastUpdated = Date()
                )

                if (currentState.id == -1) {
                    itemRepository.addItem(item)
                } else {
                    itemRepository.updateItem(item)
                }

                _uiState.update {
                    it.copy(
                        isSaving = false,
                        isSaveSuccessful = true
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isSaving = false,
                        errorMessage = e.message ?: "Unknown error while saving"
                    )
                }
            }
        }
    }

    private fun validatePrice(price: String): String? {
        if (price.isBlank()) return "Price is required"

        return try {
            val priceValue = price.toDouble()
            if (priceValue < 0) "Price cannot be negative" else null
        } catch (e: NumberFormatException) {
            "Invalid price format"
        }
    }

    private fun validateQuantity(quantity: String): String? {
        if (quantity.isBlank()) return "Quantity is required"

        return try {
            val quantityValue = quantity.toInt()
            if (quantityValue < 0) "Quantity cannot be negative" else null
        } catch (e: NumberFormatException) {
            "Invalid quantity format"
        }
    }
}