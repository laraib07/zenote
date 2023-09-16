package com.laraib07.zenote.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DateItem(
    date: Long,
    prompt: String,
    textStyle: TextStyle,
    modifier: Modifier = Modifier
) {
    val dateInStr = SimpleDateFormat(
        "dd/MM/yyyy HH:mm",
        Locale.getDefault(Locale.Category.FORMAT)
    ).format(date)
    Text(
        text = "$prompt $dateInStr",
        style = textStyle,
        color = Color.Gray,
        modifier = modifier
    )
}