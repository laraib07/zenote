package com.laraib07.zenote.ui.screen.common

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.laraib07.zenote.R
import com.laraib07.zenote.data.model.BaseNote
import com.laraib07.zenote.data.model.Folder
import com.laraib07.zenote.data.model.Note
import com.laraib07.zenote.data.model.Todo
import com.laraib07.zenote.ui.theme.noteContentTextStyle
import java.lang.Integer.max
import java.lang.Integer.min

private const val TAG = "ShowNotesContent"

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ShowNotesContent(
    paddingValues: PaddingValues,
    notes: List<Note>,
    onNoteItemTap: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalStaggeredGrid(
        modifier = modifier
            .padding(paddingValues)
            .fillMaxSize(),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 8.dp,
            bottom = 96.dp
        ),
        verticalItemSpacing = 4.dp,
        columns = StaggeredGridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(notes) { note ->
            NoteItem(
                note = note,
                onTap = {
                    Log.d(TAG, "NoteItem tapped with id : ${note.baseNote.noteId}")
                    onNoteItemTap(note.baseNote.noteId)
                }
            )
        }
    }
}

@Composable
private fun NoteItem(
    note: Note,
    onTap: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable { onTap() }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(modifier = Modifier) {
                Text(
                    text = note.baseNote.noteTitle.ifEmpty {
                        "Untitled"
                    },
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.labelLarge.copy(fontSize = 16.sp),
                    maxLines = 10,
                    overflow = TextOverflow.Ellipsis
                )
                when (note.baseNote.folder) {
                    Folder.ARCHIVED -> {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_archived_folder),
                            contentDescription = "Is archived",
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Folder.DELETED -> {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_deleted_folder),
                            contentDescription = "Is deleted",
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Folder.NOTE -> {
                        if (note.baseNote.isPinned) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_pin_filled),
                                contentDescription = "Is pinned",
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }

            }

            if (note.baseNote.isList) {
                Spacer(modifier = Modifier.height(4.dp))
                NoteItemChecklistContent(
                    todos = note.todoList.let { todos ->
                        todos.subList(0, min(todos.size, 5))
                    },
                    remainingItemCount = max(0, note.todoList.size - 5)
                )
            } else {
                note.baseNote.noteContent?.let {
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = note.baseNote.noteContent,
                        style = MaterialTheme.typography.noteContentTextStyle.copy(fontSize = 14.sp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 10,
                    )
                }
            }
        }
    }
}

@Composable
fun NoteItemChecklistContent(
    todos: List<Todo>,
    remainingItemCount: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        todos.forEach { todo ->
            TodoItem(
                todo = todo,
                textStyle = MaterialTheme.typography.noteContentTextStyle.copy(fontSize = 14.sp),
                textColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                modifier = Modifier.padding(end = 8.dp),
                size = 16.dp,
                enabled = false

            )
        }
        if (remainingItemCount > 0) {
            Text(
                text = "+$remainingItemCount items",
                style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic),
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
            )
        }
    }
}

@Preview
@Composable
private fun NoteItemPreview() {
    NoteItem(
        note = Note(
            baseNote = BaseNote.getEmptyBaseNote().copy(
                noteTitle = "Eat more green veg",
                noteContent = "And do exercise more"
            ),
            todoList = emptyList(),
            tags = emptyList()
        ),
        onTap = {}
    )
}

@Preview
@Composable
fun NoteItemPinPreview() {
    NoteItem(
        note = Note(
            baseNote = BaseNote.getEmptyBaseNote().copy(
                noteTitle = "Eat more green veg",
                noteContent = "This is a test note.\n\nView this in preview."
            ),
            todoList = emptyList(),
            tags = emptyList()
        ),
        onTap = {}
    )
}

@Preview
@Composable
fun NoteItemPinPreview2() {
    NoteItem(
        note = Note(
            baseNote = BaseNote.getEmptyBaseNote().copy(
                noteTitle = "Long Title containing more than one " +
                        "word so that the title will exceed one line",
                noteContent = "This is a test note.\n\nView this in preview."
            ),
            todoList = emptyList(),
            tags = emptyList()
        ),
        onTap = {}
    )
}

@Preview
@Composable
fun NoteItemPinPreview3() {
    NoteItem(
        note = Note(
            baseNote = BaseNote.getEmptyBaseNote().copy(
                noteTitle = "Title",
                noteContent = "In accusantium voluptatum eos ut mollitia. Aut et ut laborum. " +
                        "Et quo vitae aut. Omnis voluptas magni repudiandae est accusantium " +
                        "libero ab ut. Rerum corporis rerum molestias quibusdam ex quis. Esse " +
                        "cupiditate reprehenderit sit."
            ),
            todoList = emptyList(),
            tags = emptyList()
        ),
        onTap = {}
    )
}

