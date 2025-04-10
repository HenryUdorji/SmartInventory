package com.smartinventory.app.ui.screens.itemlist

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.smartinventory.app.data.model.Item
import com.smartinventory.app.ui.components.ItemCard
import com.smartinventory.app.ui.components.OfflineBanner

/**
 * @Author: ifechukwu.udorji
 * @Date: 4/10/2025
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemListScreen(
    onItemClick: (Int) -> Unit,
    onAddItemClick: () -> Unit,
    viewModel: ItemListViewModel = hiltViewModel()
) {
    val items by viewModel.filteredItems.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isOnline by viewModel.isOnline.collectAsState()
    val categoriesWithCounts by viewModel.categoriesWithCounts.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    var showCategoryFilter by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("Inventory Items") },
                    actions = {
                        IconButton(onClick = { showCategoryFilter = !showCategoryFilter }) {
                            Icon(
                                imageVector = Icons.Default.FilterList,
                                contentDescription = "Filter by category"
                            )
                        }
                        IconButton(onClick = { viewModel.refreshItems(true) }) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Refresh"
                            )
                        }
                        // Search icon for future implementation
                        IconButton(onClick = { /* TODO: Implement search */ }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search"
                            )
                        }
                    }
                )

                if (!isOnline) {
                    OfflineBanner()
                }

                // Category filter chips
                if (showCategoryFilter) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        categoriesWithCounts.forEach { (category, count) ->
                            FilterChip(
                                selected = selectedCategory == category,
                                onClick = { viewModel.selectCategory(category) },
                                label = { Text("$category ($count)") },
                                modifier = Modifier.padding(end = 8.dp)
                            )

                            Spacer(modifier = Modifier.width(4.dp))
                        }
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddItemClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Item"
                )
            }
        }
    ) { paddingValues ->
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            ItemList(
                items = items,
                onItemClick = onItemClick,
                contentPadding = paddingValues
            )
        }
    }
}

@Composable
fun ItemList(
    items: List<Item>,
    onItemClick: (Int) -> Unit,
    contentPadding: PaddingValues
) {
    if (items.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No items found",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Add items using the + button",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = contentPadding
        ) {
            items(items) { item ->
                ItemCard(
                    item = item,
                    onClick = { onItemClick(item.id) }
                )
            }
        }
    }
}