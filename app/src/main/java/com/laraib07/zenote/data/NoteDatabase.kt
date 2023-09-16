package com.laraib07.zenote.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.laraib07.zenote.data.dao.NoteDao
import com.laraib07.zenote.data.dao.TagDao
import com.laraib07.zenote.data.dao.TodoDao
import com.laraib07.zenote.data.model.*


@Database(
    entities = [
        BaseNote::class,
        Tag::class,
        Todo::class,
        BaseNoteTagCrossRef::class
    ],
    version = 1,
    exportSchema = false
)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
    abstract fun tagDao(): TagDao
    abstract fun todoDao(): TodoDao

    companion object {
        const val DATABASE_NAME = "note_db"
    }
}