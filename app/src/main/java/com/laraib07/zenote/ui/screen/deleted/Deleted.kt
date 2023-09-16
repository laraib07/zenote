package com.laraib07.zenote.ui.screen.deleted

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.laraib07.zenote.R
import com.laraib07.zenote.ui.components.Placeholder
import com.laraib07.zenote.ui.navigation.RootScreen
import com.laraib07.zenote.ui.screen.common.ShowNotesContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeletedScreen(
    onTapDrawer: () -> Unit,
    rootNavController: NavController,
    modifier: Modifier = Modifier,
    viewModel: DeletedViewModel = hiltViewModel()
) {
    val notes by viewModel.notes.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            DeletedScreenAppBar(
                onTapDrawer = onTapDrawer,
                onSearchActionTap = {
                    rootNavController.navigate(RootScreen.Search.route)
                }
            )
        },
        modifier = modifier
    ) { paddingValues ->
        if (notes.isEmpty()) {
            Placeholder(
                paddingValues = paddingValues,
                iconResId = R.drawable.ic_deleted_folder,
                placeholderDescId = R.string.ds_placeholder_desc
            )
        } else {
            ShowNotesContent(
                paddingValues = paddingValues,
                notes = notes,
                onNoteItemTap = {
                    rootNavController.navigate(
                        RootScreen.AddEdit.route + "?noteId=${it}"
                    )
                }
            )
        }
    }

}