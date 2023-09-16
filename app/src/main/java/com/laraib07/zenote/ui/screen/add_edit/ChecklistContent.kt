package com.laraib07.zenote.ui.screen.add_edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.laraib07.zenote.data.model.Todo
import com.laraib07.zenote.ui.screen.common.TodoItem
import com.laraib07.zenote.ui.screen.common.NoteEditorTextField

@Composable
internal fun ChecklistContent(
    viewMode: NoteViewMode,
    todos: List<Todo>,
    titleTVF: TextFieldValue,
    onTitleChange: (TextFieldValue) -> Unit
) {
    when (viewMode) {
        NoteViewMode.EDIT -> {
//            ChecklistEditor(
//                titleTFV = titleTVF,
//                onTitleChange = onTitleChange,
//                todolist = todos
//            )
            ChecklistPreview(todolist = todos, titleTFV = titleTVF)
        }

        NoteViewMode.PREVIEW -> {
            ChecklistPreview(todolist = todos, titleTFV = titleTVF)
        }
    }
}

@Composable
fun ChecklistEditor(
    titleTFV: TextFieldValue,
    onTitleChange: (TextFieldValue) -> Unit,
    todolist: List<Todo>,
    modifier: Modifier = Modifier
) {
    val titleFocusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .imePadding()
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.imePadding()
        ) {
            item {
                NoteEditorTextField(
                    value = titleTFV,
                    onChangedVal = onTitleChange,
                    placeholder = "Title",
                    textStyle = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(vertical = 16.dp)
                        .focusRequester(titleFocusRequester)
                )
            }
        }
    }
}

@Composable
fun ChecklistPreview(
    todolist: List<Todo>,
    titleTFV: TextFieldValue,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .imePadding()
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.imePadding()
        ) {
            Text(
                text = titleTFV.text,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 16.dp)
            )
            todolist.forEach { todo ->
                TodoItem(
                    todo = todo,
                    modifier = Modifier.padding(12.dp),
                    textColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
                )
            }
        }
    }
}
