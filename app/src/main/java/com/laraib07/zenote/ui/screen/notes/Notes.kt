package com.laraib07.zenote.ui.screen.notes

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.laraib07.zenote.R
import com.laraib07.zenote.ui.components.Placeholder
import com.laraib07.zenote.ui.screen.common.ShowNotesContent
import com.laraib07.zenote.ui.navigation.RootScreen

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    onTapDrawer: () -> Unit,
    rootNavController: NavController,
    modifier: Modifier = Modifier,
    viewModel: NotesViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState(initial = LatestNotesUiState())
    var openSortDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            NotesScreenAppBar(
                onTapDrawer = onTapDrawer,
                onSearchActionTap = {
                    rootNavController.navigate(RootScreen.Search.route)
                },
                onSortActionTap = { openSortDialog = !openSortDialog }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text(text = "Note") },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(id = R.string.ns_fab_desc)
                    )
                },
                onClick = {
                    rootNavController.navigate(RootScreen.AddEdit.route) {
                        launchSingleTop = true
                    }
                },
            )
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        AnimatedContent(targetState = uiState.isLoading) { isLoading ->
            when (isLoading) {
                true -> {
//                    ShowNotesShimmer(paddingValues = paddingValues)
                }

                false -> {
                    when (uiState.notes.isEmpty()) {
                        true -> {
                            Placeholder(
                                paddingValues = paddingValues,
                                iconResId = R.drawable.ic_notescreen_placeholder,
                                placeholderDescId = R.string.ns_placeholder_desc
                            )
                        }

                        false -> {
                            ShowNotesContent(
                                paddingValues = paddingValues,
                                notes = uiState.notes,
                                onNoteItemTap = { noteId ->
                                    rootNavController.navigate(
                                        RootScreen.AddEdit.withNoteId(noteId)
                                    )
                                }
                            )
                        }
                    }

                }
            }
        }
        if (openSortDialog) {
            SortMenuDialog(
                noteOrder = uiState.order,
                onDismiss = { openSortDialog = false },
                onConfirm = {
                }
            )
        }
    }
}