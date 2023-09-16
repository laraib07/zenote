package com.laraib07.zenote.ui.screen.common

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.laraib07.zenote.data.model.Todo
import com.laraib07.zenote.ui.theme.ZenoteTheme
import com.laraib07.zenote.ui.theme.noteContentTextStyle

@Composable
fun TodoItem(
    todo: Todo,
    modifier: Modifier = Modifier,
    onTap: (Boolean) -> Unit = {},
    size: Dp = 20.dp,
    enabled: Boolean = true,
    textStyle: TextStyle = MaterialTheme.typography.noteContentTextStyle,
    textColor: Color = MaterialTheme.colorScheme.onBackground
) {
    var checked by remember { mutableStateOf(todo.isDone) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RoundedCheckBox(
            checked = checked,
            onCheckedChange = { checked = !checked },
            modifier = modifier,
            size = size,
            enabled = enabled
        )
        Text(
            text = todo.todoContent,
            style = if (checked) {
                textStyle.copy(
                    textDecoration = TextDecoration.LineThrough
                )
            } else {
                textStyle
            },
            color = if (checked) {
                textColor.copy(alpha = 0.5f)
            } else {
                textColor
            },
            modifier = Modifier.padding(vertical = 4.dp),
            maxLines = if (enabled) Int.MAX_VALUE else 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChecklistItemPreview() {
    TodoItem(
        todo = Todo(
            isDone = true,
            todoContent = "Eat more green veg",
            todoNoteId = 0L,
            todoId = 0L
        ),
        onTap = {}
    )
}

@Preview(showBackground = true)
@Composable
fun ChecklistItemPreview2() {
    TodoItem(
        todo = Todo(
            isDone = false,
            todoContent = "Eat more green veg",
            todoNoteId = 0L,
            todoId = 0L
        ),
        modifier = Modifier.padding(16.dp),
        onTap = {}
    )
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    showSystemUi = false,
    wallpaper = Wallpapers.RED_DOMINATED_EXAMPLE
)
@Composable
fun ChecklistItemPreview3() {
    ZenoteTheme {
        TodoItem(
            todo = Todo(
                isDone = true,
                todoContent = "Eat more green veg",
                todoNoteId = 0L,
                todoId = 0L
            ),
            modifier = Modifier.padding(16.dp),
            onTap = {}
        )
    }
}

