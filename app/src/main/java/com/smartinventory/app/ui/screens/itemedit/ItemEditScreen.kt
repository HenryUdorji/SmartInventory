package com.smartinventory.app.ui.screens.itemedit

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.smartinventory.app.ui.components.OfflineBanner

/**
 * @Author: ifechukwu.udorji
 * @Date: 4/10/2025
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemEditScreen(
    itemId: Int = -1, // -1 means we're adding a new item
    onBackClick: () -> Unit,
    onSaveSuccess: () -> Unit,
    viewModel: ItemEditViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val isOnline by viewModel.isOnline.collectAsState()

    LaunchedEffect(itemId) {
        viewModel.initializeScreen(itemId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (itemId == -1) "Add Item" else "Edit Item") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.saveItem() },
                        enabled = uiState.isValid && !uiState.isSaving
                    ) {
                        Icon(Icons.Default.Save, contentDescription = "Save")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            if (!isOnline) {
                OfflineBanner()
            }

            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                uiState.errorMessage != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Error: ${uiState.errorMessage}")
                    }
                }
                uiState.isSaveSuccessful -> {
                    LaunchedEffect(Unit) {
                        onSaveSuccess()
                    }
                }
                else -> {
                    ItemEditForm(
                        uiState = uiState,
                        onNameChange = viewModel::onNameChange,
                        onDescriptionChange = viewModel::onDescriptionChange,
                        onPriceChange = viewModel::onPriceChange,
                        onQuantityChange = viewModel::onQuantityChange,
                        onCategoryChange = viewModel::onCategoryChange,
                        onImageUrlChange = viewModel::onImageUrlChange,
                        onSupplierNameChange = viewModel::onSupplierNameChange,
                        onSupplierContactChange = viewModel::onSupplierContactChange
                    )
                }
            }

            if (uiState.isSaving) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ItemEditForm(
    uiState: ItemEditUiState,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onPriceChange: (String) -> Unit,
    onQuantityChange: (String) -> Unit,
    onCategoryChange: (String) -> Unit,
    onImageUrlChange: (String) -> Unit,
    onSupplierNameChange: (String) -> Unit,
    onSupplierContactChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Name
        OutlinedTextField(
            value = uiState.name,
            onValueChange = onNameChange,
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.nameError != null,
            supportingText = {
                if (uiState.nameError != null) {
                    Text(uiState.nameError)
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Description
        OutlinedTextField(
            value = uiState.description,
            onValueChange = onDescriptionChange,
            label = { Text("Description") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            isError = uiState.descriptionError != null,
            supportingText = {
                if (uiState.descriptionError != null) {
                    Text(uiState.descriptionError)
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Price
        OutlinedTextField(
            value = uiState.price,
            onValueChange = onPriceChange,
            label = { Text("Price") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            isError = uiState.priceError != null,
            supportingText = {
                if (uiState.priceError != null) {
                    Text(uiState.priceError)
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Quantity
        OutlinedTextField(
            value = uiState.quantity,
            onValueChange = onQuantityChange,
            label = { Text("Quantity") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = uiState.quantityError != null,
            supportingText = {
                if (uiState.quantityError != null) {
                    Text(uiState.quantityError)
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Category
        OutlinedTextField(
            value = uiState.category,
            onValueChange = onCategoryChange,
            label = { Text("Category") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.categoryError != null,
            supportingText = {
                if (uiState.categoryError != null) {
                    Text(uiState.categoryError)
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Image URL
        OutlinedTextField(
            value = uiState.imageUrl,
            onValueChange = onImageUrlChange,
            label = { Text("Image URL") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.imageUrlError != null,
            supportingText = {
                if (uiState.imageUrlError != null) {
                    Text(uiState.imageUrlError)
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Supplier Information
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Supplier Information",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = uiState.supplierName,
                    onValueChange = onSupplierNameChange,
                    label = { Text("Supplier Name") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = uiState.supplierNameError != null,
                    supportingText = {
                        if (uiState.supplierNameError != null) {
                            Text(uiState.supplierNameError)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = uiState.supplierContact,
                    onValueChange = onSupplierContactChange,
                    label = { Text("Supplier Contact") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = uiState.supplierContactError != null,
                    supportingText = {
                        if (uiState.supplierContactError != null) {
                            Text(uiState.supplierContactError)
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}