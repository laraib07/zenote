package com.laraib07.zenote.ui.screen.search

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.laraib07.zenote.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {
    private val _searchQuery = MutableStateFlow(TextFieldValue())
    val searchQuery = _searchQuery.asStateFlow()

    private val _notes = repository.getAllNotes
    val notes = searchQuery
        .combine(_notes) { query, notes ->
            if (query.text.isBlank()) {
                emptyList()
            } else {
                notes.filter { note ->
                    note.doesMatchSearchQuery(query.text)
                }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun onSearchQueryChange(query: TextFieldValue) {
        _searchQuery.update { query }
    }
}