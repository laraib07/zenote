package com.laraib07.zenote.ui.screen.todos

import androidx.lifecycle.ViewModel
import androidx.room.Insert
import com.laraib07.zenote.data.repository.NoteRepository
import com.laraib07.zenote.data.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TodosViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {
    val todos = repository.getAllTodos()
}