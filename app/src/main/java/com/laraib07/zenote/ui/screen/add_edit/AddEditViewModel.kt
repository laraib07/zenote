package com.laraib07.zenote.ui.screen.add_edit

import android.util.Log
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.laraib07.zenote.data.model.*
import com.laraib07.zenote.data.repository.NoteRepository
import com.laraib07.zenote.data.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

private const val TAG = "AddEditViewModel"

@HiltViewModel
class AddEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val noteRepository: NoteRepository,
    private val todoRepository: TodoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddEditUiState())
    val uiState = _uiState.asStateFlow()
    private var uiStateCopy = uiState.value

    init {
        val noteId: Long = checkNotNull(savedStateHandle["noteId"])
        Log.d(TAG, "NoteId from savedStateHandle: $noteId")

        fetchUpdates(noteId)
    }

    private fun fetchUpdates(noteId: Long) {
        Log.d(TAG, "fetchUpdates()")

        viewModelScope.launch(Dispatchers.IO) {
            if (noteId == 0L) {
                _uiState.update {
                    it.copy(
                        noteId = noteRepository.insertBaseNote(BaseNote.getEmptyBaseNote()),
                        viewMode = NoteViewMode.EDIT
                    )
                }
            } else {
                _uiState.update { it.copy(noteId = noteId) }
            }

            Log.d(TAG, "NoteId: ${uiState.value.noteId}")
            noteRepository.getNoteById(uiState.value.noteId).collect { note ->
                note?.let { updateNoteUiState(it) }
            }
        }
    }

    private fun updateNoteUiState(note: Note) {
        Log.d(TAG, "updateNoteUiState()")

        _uiState.update {
            it.copy(
                noteId = note.baseNote.noteId,
                titleTFV = if (uiState.value.titleTFV.text.isEmpty()) {
                    // Move Cursor to the end
                    uiState.value.titleTFV.copy(
                        note.baseNote.noteTitle,
                        TextRange(note.baseNote.noteTitle.length)
                    )
                } else {
                    uiState.value.titleTFV.copy(note.baseNote.noteTitle)
                },
                contentTFV = if (uiState.value.contentTFV.text.isEmpty()) {
                    // Move Cursor to the end
                    uiState.value.contentTFV.copy(
                        note.baseNote.noteContent ?: "",
                        TextRange(note.baseNote.noteContent?.length ?: 0)
                    )
                } else {
                    uiState.value.contentTFV.copy(note.baseNote.noteContent ?: "")
                },
                pinState = note.baseNote.isPinned,
                noteContentMode = if (note.baseNote.isList) {
                    NoteContentMode.CHECKLIST
                } else {
                    NoteContentMode.NOTE
                },
                todos = note.todoList
            )
        }
        uiStateCopy = uiState.value
    }

    fun onTitleChange(title: TextFieldValue) {
        _uiState.update { it.copy(titleTFV = title) }
    }

    fun onContentChange(content: TextFieldValue) {
        _uiState.update { it.copy(contentTFV = content) }
    }

    fun onPinStateChange() {
        Log.d(TAG, "onPinStateChange()")
        viewModelScope.launch(Dispatchers.IO) {
            if (noteIsModified()) {
                Log.d(TAG, "onPinStateChange(): Note is Modified, Saving note")
                noteRepository.updateTitleAndContent(
                    title = uiState.value.titleTFV.text,
                    content = uiState.value.contentTFV.text,
                    noteId = uiState.value.noteId
                )
            }
            noteRepository.updatePinStatus(
                !uiState.value.pinState,
                uiState.value.noteId
            )
        }
    }

    fun onViewModeChange(callback: () -> Unit?) {
        Log.d(TAG, "onViewModeChange()")
        _uiState.update {
            it.copy(
                viewMode = when (uiState.value.viewMode) {
                    NoteViewMode.PREVIEW -> NoteViewMode.EDIT
                    NoteViewMode.EDIT -> {
                        callback()
                        addNote()
                        NoteViewMode.PREVIEW
                    }
                }
            )
        }
    }

    fun onNoteContentModeChange() {
        Log.d(TAG, "onNoteContentModeChange()")
        if (uiState.value.noteContentMode.isChecklist()) {
            changeToNote()
        } else {
            changeToList()
        }
    }

    private fun changeToList() {
        Log.d(TAG, "changeToList()")

        val content = uiState.value.contentTFV.text
        addTodos(content)
    }

    private fun changeToNote() {
        Log.d(TAG, "changeToNote()")
        val content = Note.todoListToContent(uiState.value.todos)
        removeTodos(content)
    }

    private fun isNotEmpty(): Boolean {
        return uiState.value.titleTFV.text.isNotEmpty() ||
                uiState.value.contentTFV.text.isNotEmpty()

    }

    private fun noteIsModified(): Boolean {
        return uiState.value.titleTFV.text.trim() != uiStateCopy.titleTFV.text ||
                uiState.value.contentTFV.text.trim() != uiStateCopy.contentTFV.text ||
                uiState.value.pinState != uiStateCopy.pinState ||
                uiState.value.noteContentMode != uiStateCopy.noteContentMode ||
                uiState.value.todos != uiStateCopy.todos
    }

    fun addNote() {
        if (isNotEmpty()) {
            if (noteIsModified()) {
                val currentTime = Date().time

                val baseNote = BaseNote(
                    noteId = uiState.value.noteId,
                    noteTitle = uiState.value.titleTFV.text.trim(),
                    noteContent = uiState.value.contentTFV.text.trim().ifEmpty { null },
                    created = currentTime,
                    modified = currentTime,
                    isPinned = uiState.value.pinState,
                    folder = Folder.NOTE,
                    isList = uiState.value.noteContentMode.isChecklist(),
                )
                val tags = listOf<Tag>()
                val todoList = uiState.value.todos
                val newNote = Note(
                    baseNote = baseNote,
                    todoList = todoList,
                    tags = tags
                )

                Log.d(TAG, "Saving note")
                uiStateCopy = uiState.value
                viewModelScope.launch(Dispatchers.IO) {
                    noteRepository.addNote(newNote)
                }
            }
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                noteRepository.removeNoteById(
                    uiState.value.noteId
                )
            }
        }
    }

    private fun addTodos(content: String) {
        val newTodos = Note.contentToTodoList(
            content = content,
            noteId = uiState.value.noteId,
        )
        Log.d(TAG, "addTodos() with ${newTodos.size} todos")
        viewModelScope.launch(Dispatchers.IO) {
            todoRepository.addTodos(newTodos)
            noteRepository.updateListStatus(
                title = uiState.value.titleTFV.text,
                content = null,
                isList = true,
                noteId = uiState.value.noteId
            )
        }
    }

    private fun removeTodos(content: String) {
        Log.d(TAG, "removeTodos()")

        viewModelScope.launch(Dispatchers.IO) {
            todoRepository.removeTodos(uiState.value.todos)
            noteRepository.updateListStatus(
                title = uiState.value.titleTFV.text,
                content = content,
                isList = false,
                noteId = uiState.value.noteId
            )
        }
    }
}

data class AddEditUiState(
    val noteId: Long = 0L,
    val titleTFV: TextFieldValue = TextFieldValue(),
    val contentTFV: TextFieldValue = TextFieldValue(),
    val pinState: Boolean = false,
    val todos: List<Todo> = listOf(),
    val viewMode: NoteViewMode = NoteViewMode.PREVIEW,
    val noteContentMode: NoteContentMode = NoteContentMode.NOTE,
) {
    val isPreview = viewMode.isPreview()
}
