package com.laraib07.zenote.ui.screen.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha

@Composable
fun SearchAction(
    modifier: Modifier = Modifier,
    onSearchTap: () -> Unit = {}
) {
    IconButton(
        modifier = modifier.alpha(0.8f),
        onClick = onSearchTap
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search"
        )
    }
}