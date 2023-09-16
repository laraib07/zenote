package com.laraib07.zenote.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = BaseNote.TABLE_NAME)
data class BaseNote(
    val noteTitle: String,
    val noteContent: String? = null,
    val created: Long,
    val modified: Long,
    val folder: Folder,
    val isList: Boolean,
    val isPinned: Boolean = false,
    @PrimaryKey(autoGenerate = true) val noteId: Long = 0L,
) {
    companion object {
        const val TABLE_NAME = "note_table"

        fun getEmptyBaseNote() = BaseNote(
            noteTitle = "",
            noteContent = "",
            created = 0L,
            modified = 0L,
            folder = Folder.NOTE,
            isList = false,
            isPinned = false
        )
    }
}

data class Note(
    @Embedded val baseNote: BaseNote,
    @Relation(
        parentColumn = "noteId",
        entityColumn = "todoNoteId"
    )
    val todoList: List<Todo>,
    @Relation(
        parentColumn = "noteId",
        entityColumn = "tagId",
        associateBy = Junction(BaseNoteTagCrossRef::class)
    )
    val tags: List<Tag>
) {
    fun doesMatchSearchQuery(query: String): Boolean {
        if (baseNote.noteTitle.contains(query, ignoreCase = true))
            return true

        if (tags.any { it.tagContent.contains(query, ignoreCase = true) })
            return true

        if (baseNote.isList) {
            return todoList.any {
                it.todoContent.contains(query, ignoreCase = true)
            }
        }

        return baseNote.noteContent?.contains(query, ignoreCase = true) ?: false
    }

    companion object {
        fun contentToTodoList(content: String?, noteId: Long): List<Todo> {
            return content
                ?.lines()
                ?.filter { it.isNotEmpty() }
                ?.map {
                    Todo(
                        todoNoteId = noteId,
                        isDone = false,
                        todoContent = it.trim()
                    )
                }
                ?: emptyList()
        }

        fun todoListToContent(todos: List<Todo>): String {
            if (todos.isEmpty())
                return ""

            return todos.joinToString("\n") {
                it.todoContent.trim()
            }
        }
    }
}