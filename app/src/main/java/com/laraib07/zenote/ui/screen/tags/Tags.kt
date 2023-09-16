package com.laraib07.zenote.ui.screen.tags

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.laraib07.zenote.R
import com.laraib07.zenote.ui.components.Placeholder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagsScreen(
    onTapDrawer: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TagsViewModel = hiltViewModel()
) {
    val openDialog = remember { mutableStateOf(false) }
    val tagTFV = viewModel.tagTFV
    val tags by viewModel.tagsList.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.tags)) },
                navigationIcon = {
                    IconButton(onClick = onTapDrawer) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = stringResource(id = R.string.toggle_drawer),
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { openDialog.value = true }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add_tag_dialog)
                )
            }
        },
        modifier = modifier
    ) {
        if (tags.isEmpty()) {
            Placeholder(
                paddingValues = it,
                iconResId = R.drawable.ic_tagscreen_placeholder,
                placeholderDescId = R.string.ts_placeholder_desc
            )
        } else {
            TagsContent(
                paddingValues = it,
                tags = tags
            )
        }
        if (openDialog.value) {
            AddTagDialog(
                tagTFV = tagTFV,
                onTagTFVChange = viewModel::onTagTFVChange,
                onConfirm = {
                    viewModel.addTags()
                    openDialog.value = false
                },
                onDismiss = {
                    openDialog.value = false
                }
            )
        }
    }
}


@Preview
@Composable
fun TagsScreenPreview() {
    TagsScreen({})
}