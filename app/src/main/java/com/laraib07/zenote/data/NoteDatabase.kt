package com.laraib07.zenote.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.laraib07.zenote.data.converter.LongListConverter
import com.laraib07.zenote.data.converter.TodoListConverter
import com.laraib07.zenote.data.dao.NoteDao
import com.laraib07.zenote.data.dao.TagDao
import com.laraib07.zenote.data.dao.TodoDao
import com.laraib07.zenote.data.model.*


@TypeConverters(TodoListConverter::class, LongListConverter::class)
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