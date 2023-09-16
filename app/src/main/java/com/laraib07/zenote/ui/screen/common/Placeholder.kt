package com.laraib07.zenote.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.laraib07.zenote.R

@Composable
internal fun Placeholder(
    paddingValues: PaddingValues,
    iconResId: Int,
    placeholderDescId: Int,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = stringResource(id = placeholderDescId),
            tint = MaterialTheme.colorScheme.surfaceTint,
            modifier = Modifier.size(128.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = placeholderDescId),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ArchivePlaceholderPreview() {
    Placeholder(
        paddingValues = PaddingValues(),
        iconResId = R.drawable.ic_archived_folder,
        placeholderDescId = R.string.as_placeholder_desc
    )
}

@Preview(showBackground = true)
@Composable
private fun NotesPlaceholderPreview() {
    Placeholder(
        paddingValues = PaddingValues(),
        iconResId = R.drawable.ic_notescreen_placeholder,
        placeholderDescId = R.string.ns_placeholder_desc
    )
}

@Preview(showBackground = true)
@Composable
private fun DeletedPlaceholderPreview() {
    Placeholder(
        paddingValues = PaddingValues(),
        iconResId = R.drawable.ic_deleted_folder,
        placeholderDescId = R.string.ds_placeholder_desc
    )
}

@Preview(showBackground = true)
@Composable
private fun TagsPlaceholderPreview() {
    Placeholder(
        paddingValues = PaddingValues(),
        iconResId = R.drawable.ic_tagscreen_placeholder,
        placeholderDescId = R.string.ts_placeholder_desc
    )
}