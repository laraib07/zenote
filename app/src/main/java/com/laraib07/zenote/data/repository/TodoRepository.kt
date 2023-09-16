package com.laraib07.zenote.data.repository

import com.laraib07.zenote.data.dao.TodoDao
import com.laraib07.zenote.data.model.Todo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoRepository @Inject constructor(
    private val todoDao: TodoDao
) {

    fun getAllTodos(): Flow<List<Todo>> {
        return todoDao.getAllTodos()
    }

    suspend fun getNextTodoId(): Long {
        return todoDao.getMaxTodoId()?.plus(1L) ?: 1L
    }

    suspend fun addTodos(todos: List<Todo>) {
        todoDao.upsertTodo(todos)
    }

    suspend fun removeTodos(todos: List<Todo>) {
        todoDao.deleteTodo(todos)
    }
}