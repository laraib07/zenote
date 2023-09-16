package com.laraib07.zenote.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Todo.TABLE_NAME)
data class Todo(
    var isDone: Boolean,
    val todoContent: String,
    val todoNoteId: Long,
    @PrimaryKey(autoGenerate = true) val todoId: Long = 0L
) {
    companion object {
        const val TABLE_NAME = "todo_table"
    }
}
