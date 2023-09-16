package com.laraib07.zenote.ui.screen.todos

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.laraib07.zenote.R
import com.laraib07.zenote.ui.screen.common.TodoItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodosScreen(
    onTapDrawer: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TodosViewModel = hiltViewModel()
) {
    val todos by viewModel.todos.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.todos)) },
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
        modifier = modifier
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            items(todos) { todo ->
                TodoItem(todo = todo)
            }
        }
    }
}
