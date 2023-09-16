package com.laraib07.zenote.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun UnderDevelopment(paddingValues: PaddingValues = PaddingValues()) {
    Box(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth()
            .height(200.dp)
            .border(4.dp, Color.Yellow),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(all = 16.dp),
            text = "Under Development",
            color = Color.Red
        )
    }
}