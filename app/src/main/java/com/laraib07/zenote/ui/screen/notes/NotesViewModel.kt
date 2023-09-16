package com.laraib07.zenote.ui.screen.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.laraib07.zenote.data.model.Folder
import com.laraib07.zenote.data.model.Note
import com.laraib07.zenote.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(LatestNotesUiState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            repository.getAllNotes.collect { noteList ->
                _uiState.update {
                    it.copy(
                        notes = noteList.filter { note ->
                            note.baseNote.folder == Folder.NOTE
                        },
                        isLoading = false
                    )
                }
            }
        }
    }
}

data class LatestNotesUiState(
    val notes: List<Note> = emptyList(),
    val order: NoteOrder = NoteOrder.MODIFIED(orderType = OrderType.DESCENDING),
    val isLoading: Boolean = true
)