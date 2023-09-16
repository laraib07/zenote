package com.laraib07.zenote.ui.screen.add_edit

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.laraib07.zenote.R
import com.laraib07.zenote.ui.components.UnderDevelopment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddEditScreenAppBar(
    pinState: Boolean,
    noteMode: NoteContentMode,
    onPinTapped: () -> Unit,
    onNoteContentChange: () -> Unit,
    onBack: () -> Unit,
    isPreview: Boolean,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        title = {},
        navigationIcon = {
            IconButton(
                modifier = Modifier.alpha(0.8f),
                onClick = onBack
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Go back"
                )
            }
        },
        actions = {
            AppBarActions(
                pinState = pinState,
                onPinTapped = onPinTapped,
                noteModeState = noteMode,
                onNoteModeTapped = onNoteContentChange,
                isPreview = isPreview
            )
        }
    )
}

@Composable
private fun AppBarActions(
    pinState: Boolean,
    onPinTapped: () -> Unit,
    noteModeState: NoteContentMode,
    onNoteModeTapped: () -> Unit,
    isPreview: Boolean
) {
    PinAction(state = pinState, onPinTapped = onPinTapped)
    AnimatedVisibility(visible = !isPreview) {
        NoteModeAction(state = noteModeState, onNoteModeTapped = onNoteModeTapped)
    }

    MoreActions()
}

@Composable
private fun PinAction(
    state: Boolean,
    onPinTapped: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onPinTapped,
        modifier = modifier
            .padding(horizontal = 12.dp)
            .size(24.dp)
            .alpha(0.8f)

    ) {
        Crossfade(targetState = state) { isPinned ->
            when (isPinned) {
                true -> {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_pin_filled),
                        contentDescription = "Pinned"
                    )
                }
                false -> {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_pin),
                        contentDescription = "Pinned"
                    )
                }
            }

        }
    }
}

@Composable
private fun NoteModeAction(
    state: NoteContentMode,
    onNoteModeTapped: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onNoteModeTapped,
        modifier = modifier
            .padding(horizontal = 12.dp)
            .size(24.dp)
            .alpha(0.8f)
    ) {
        Crossfade(targetState = state) { noteMode ->
            when (noteMode) {
                NoteContentMode.NOTE -> {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_checklist),
                        contentDescription = "List",
                    )
                }
                NoteContentMode.CHECKLIST -> {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_notes),
                        contentDescription = "Note"
                    )
                }
            }
        }
    }
}

@Composable
private fun MoreActions() {
    var expanded by remember { mutableStateOf(false) }

    IconButton(
        onClick = { expanded = true },
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .size(24.dp)
            .alpha(0.8f)
    ) {
        Icon(
            Icons.Default.MoreVert,
            contentDescription = "Localized description"
        )
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
    ) {
        UnderDevelopment()
    }
}


@Preview(showBackground = true)
@Composable
private fun AppBarPreview() {
    var isPinned by remember { mutableStateOf(false) }
    var noteMode by remember { mutableStateOf(NoteContentMode.NOTE) }

    Scaffold(topBar = {
        AddEditScreenAppBar(
            pinState = isPinned,
            noteMode = noteMode,
            onPinTapped = { isPinned = !isPinned },
            onNoteContentChange = {
                noteMode = if (noteMode == NoteContentMode.NOTE) {
                    NoteContentMode.CHECKLIST
                } else {
                    NoteContentMode.NOTE
                }
            },
            onBack = { },
            isPreview = true
        )
    }) {
        Spacer(modifier = Modifier.padding(it))
    }
}