package com.laraib07.zenote.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.laraib07.zenote.data.model.Todo
import kotlinx.coroutines.flow.Flow

private const val TABLE = Todo.TABLE_NAME

@Dao
interface TodoDao {

    @Upsert
    suspend fun upsertTodo(todos: List<Todo>)

    @Delete
    suspend fun deleteTodo(todos: List<Todo>)

    @Query("SELECT MAX(todoId) FROM $TABLE")
    suspend fun getMaxTodoId(): Long?

    @Query("SELECT * FROM $TABLE ORDER BY todoId DESC")
    fun getAllTodos(): Flow<List<Todo>>
}