package com.laraib07.zenote.ui.screen.add_edit

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

private const val TAG = "AddEditNote"

enum class NoteViewMode {
    EDIT,
    PREVIEW;

    fun isPreview(): Boolean = this == PREVIEW
}

enum class NoteContentMode {
    NOTE,
    CHECKLIST;

    fun isChecklist(): Boolean = this == CHECKLIST
}

@OptIn(
    ExperimentalComposeUiApi::class
)
@Composable
fun AddEditNoteScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddEditViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val keyboard = LocalSoftwareKeyboardController.current
    val scrollState = rememberScrollState()

    val tags = listOf("Ideas", "OnGoing", "Prototype", "Compose", "Jetpack", "Zenote")
    Scaffold(
        topBar = {
            AddEditScreenAppBar(
                pinState = uiState.pinState,
                noteMode = uiState.noteContentMode,
                onPinTapped = { viewModel.onPinStateChange() },
                onNoteContentChange = {
                    viewModel.onNoteContentModeChange()
                },
                isPreview = uiState.isPreview,
                onBack = {
                    viewModel.addNote()
                    onBack()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onViewModeChange {
                        keyboard?.hide()
                    }
                },
                modifier = Modifier.imePadding()
            ) {
                Icon(
                    imageVector = when (uiState.viewMode) {
                        NoteViewMode.PREVIEW -> Icons.Default.Edit
                        NoteViewMode.EDIT -> Icons.Default.Done
                    },
                    contentDescription = ""
                )
            }

        },
        modifier = modifier
    ) { paddingValues ->
        AddEditContent(
            scrollState = scrollState,
            paddingValues = paddingValues,
            viewMode = uiState.viewMode,
            noteContentMode = uiState.noteContentMode,
            isPreview = uiState.isPreview,
            titleTFV = uiState.titleTFV,
            contentTFV = uiState.contentTFV,
            onTitleChange = { viewModel.onTitleChange(it) },
            onContentChange = { viewModel.onContentChange(it) },
            tags = tags,
            todos = uiState.todos
        )
    }

    BackHandler(true) {
        viewModel.addNote()
        onBack()
    }
}

@Preview(showBackground = true)
@Composable
fun AddEditNoteScreenPreview() {
    AddEditNoteScreen({})
}