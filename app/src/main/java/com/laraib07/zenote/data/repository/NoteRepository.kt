package com.laraib07.zenote.data.repository

import com.laraib07.zenote.data.dao.NoteDao
import com.laraib07.zenote.data.dao.TagDao
import com.laraib07.zenote.data.dao.TodoDao
import com.laraib07.zenote.data.model.BaseNote
import com.laraib07.zenote.data.model.Note
import com.laraib07.zenote.data.model.Tag
import com.laraib07.zenote.data.model.Todo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepository @Inject constructor(
    private val noteDao: NoteDao,
    private val tagDao: TagDao,
    private val todoDao: TodoDao
) {

    val getAllNotes: Flow<List<Note>> = noteDao.getAllNotes()

    // Methods for NoteDao
    suspend fun addNote(note: Note) {
        noteDao.insertBaseNote(note.baseNote)
        todoDao.upsertTodo(note.todoList)
        // TODO: Add tagCrossRef and todolist
    }

    suspend fun insertBaseNote(baseNote: BaseNote): Long = noteDao.insertBaseNote(baseNote)

    suspend fun removeNote(note: Note) {
        noteDao.deleteNote(note.baseNote)
        todoDao.deleteTodo(note.todoList)
        // TODO: Remove tagCrossRef and todolist
    }

    suspend fun removeNoteById(id: Long) {
        noteDao.deleteNoteById(id)
    }

    suspend fun updateNoteContent(id: Long, content: String) {
        noteDao.updateNoteContent(id, content)
    }

    fun getNoteById(id: Long): Flow<Note?> = noteDao.getNoteById(id)

    suspend fun getNextNoteId(): Long {
        return noteDao.getMaxNoteId()?.plus(1L) ?: 1L
    }

    suspend fun updatePinStatus(pinStatus: Boolean, noteId: Long) {
        noteDao.updatePinStatus(pinStatus, noteId)
    }

    suspend fun updateListStatus(title: String, content: String?, isList: Boolean, noteId: Long) {
        noteDao.updateListStatus(
            title = title,
            content = content,
            isList = isList,
            id = noteId
        )
    }

    suspend fun updateTitleAndContent(title: String, content: String?, noteId: Long) {
        noteDao.updateTitleAndContent(
            title = title,
            content = content,
            id = noteId
        )
    }

    suspend fun removeEmptyNotes() {
        noteDao.removeEmptyNotes()
    }
}