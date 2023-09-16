package com.laraib07.zenote.data.dao

import androidx.room.*
import com.laraib07.zenote.data.model.BaseNote
import com.laraib07.zenote.data.model.Folder
import com.laraib07.zenote.data.model.Note
import kotlinx.coroutines.flow.Flow

private const val TABLE = BaseNote.TABLE_NAME

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBaseNote(baseNote: BaseNote): Long

    @Transaction
    @Query("SELECT * FROM $TABLE WHERE noteId=:id")
    fun getNoteById(id: Long): Flow<Note?>

    @Delete
    suspend fun deleteNote(baseNote: BaseNote)

    @Query("DELETE FROM $TABLE WHERE noteId = :id")
    suspend fun deleteNoteById(id: Long)

    @Transaction
    @Query("SELECT * FROM $TABLE ORDER BY isPinned DESC, modified DESC")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT MAX(noteId) FROM $TABLE")
    suspend fun getMaxNoteId(): Long?

    @Query("UPDATE $TABLE SET noteContent = :content WHERE noteId = :id")
    suspend fun updateNoteContent(id: Long, content: String)

    @Query("UPDATE $TABLE SET isPinned = :pinStatus WHERE noteId = :id")
    suspend fun updatePinStatus(pinStatus: Boolean, id: Long)

    @Query("UPDATE $TABLE SET isList = :isList WHERE noteId = :id")
    suspend fun updateListStatus(isList: Boolean, id: Long)

    @Query(
        """
            UPDATE $TABLE
            SET noteTitle = :title, noteContent = :content, isList = :isList
            WHERE noteId = :id
        """
    )
    suspend fun updateListStatus(title: String, content: String?, isList: Boolean, id: Long)

    @Query(
        """
            UPDATE $TABLE
            SET noteTitle = :title, noteContent = :content
            WHERE noteId = :id
        """
    )
    suspend fun updateTitleAndContent(title: String, content: String?, id: Long)

    @Query("UPDATE $TABLE SET folder = :folder WHERE noteId = :id")
    suspend fun updateNoteFolder(folder: Folder, id: Long)

    @Query("DELETE FROM $TABLE WHERE noteTitle = '' AND noteContent = '' AND isList = 0")
    suspend fun removeEmptyNotes()
}