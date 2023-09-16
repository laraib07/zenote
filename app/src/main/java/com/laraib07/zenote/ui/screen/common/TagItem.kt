package com.laraib07.zenote.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TagItem(
    title: String,
    modifier: Modifier = Modifier
) {
//    SuggestionChip(
//        onClick = {},
//        label = {
//            Text(
//                text = "# $title",
//                style = MaterialTheme.typography.labelMedium
//            )
//                },
//        modifier = modifier
//    )
    Box(
        modifier = modifier
            .border(1.dp, color = Color.Gray, shape = CircleShape)
            .padding(all = 8.dp)
        ,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "# $title",
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TagItemPreview() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        TagItem(title = "Ideas")
    }
}