package com.smartinventory.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * @Author: ifechukwu.udorji
 * @Date: 4/9/2025
 */
@Composable
fun OfflineBanner() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.errorContainer)
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.CloudOff,
            contentDescription = "Offline",
            tint = MaterialTheme.colorScheme.onErrorContainer
        )
        Text(
            text = "You are offline. Using cached data.",
            color = MaterialTheme.colorScheme.onErrorContainer,
            modifier = Modifier.padding(start = 8.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
