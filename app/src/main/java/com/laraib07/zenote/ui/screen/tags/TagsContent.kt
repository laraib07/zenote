package com.laraib07.zenote.ui.screen.tags

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.laraib07.zenote.R
import com.laraib07.zenote.data.model.Tag

@Composable
fun TagsContent(
    paddingValues: PaddingValues,
    tags: List<Tag>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        items(tags) { tag ->
            TagItem(tag = tag)
        }
    }
}

@Composable
private fun TagItem(
    tag: Tag,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_tags),
            contentDescription = null,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .size(24.dp),
            tint = MaterialTheme.colorScheme.onBackground.copy(0.8f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            text = tag.tagContent,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TagItemPreview() {
    TagItem(tag = Tag("Idea", 0L))
}
