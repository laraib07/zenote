package com.laraib07.zenote.ui.screen.notes

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.laraib07.zenote.R
import com.laraib07.zenote.ui.components.UnderDevelopment
import com.laraib07.zenote.ui.screen.common.SearchAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreenAppBar(
    onTapDrawer: () -> Unit,
    onSearchActionTap: () -> Unit,
    onSortActionTap: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(text = stringResource(id = R.string.all_notes_folder))
        },
        navigationIcon = {
            IconButton(
                modifier = Modifier.alpha(0.8f),
                onClick = onTapDrawer
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = stringResource(id = R.string.toggle_drawer),
                )
            }
        },
        actions = {
            AppBarActions(
                onSearchActionTap = onSearchActionTap,
                onSortActionTap = onSortActionTap
            )
        }
    )
}

@Composable
private fun AppBarActions(
    onSearchActionTap: () -> Unit,
    onSortActionTap: () -> Unit
) {
    SearchAction(onSearchTap = onSearchActionTap)
    SortAction(onSortTap = onSortActionTap)
    MoreActions()
}


@Composable
private fun SortAction(onSortTap: () -> Unit) {
    IconButton(
        modifier = Modifier.alpha(0.8f),
        onClick = onSortTap
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_sort_action),
            contentDescription = "Sort"
        )
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

@Preview
@Composable
fun NotesScreenAppBarPrev() {
    NotesScreenAppBar(
        onTapDrawer = { /*TODO*/ },
        onSearchActionTap = {},
        onSortActionTap = {}
    )
}