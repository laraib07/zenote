package com.laraib07.zenote.di

import android.content.Context
import androidx.room.Room
import com.laraib07.zenote.data.NoteDatabase
import com.laraib07.zenote.data.dao.NoteDao
import com.laraib07.zenote.data.dao.TagDao
import com.laraib07.zenote.data.dao.TodoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideNoteDao(database: NoteDatabase): NoteDao {
        return database.noteDao()
    }

    @Provides
    fun provideTagDao(database: NoteDatabase): TagDao {
        return database.tagDao()
    }

    @Provides
    fun provideTodoDao(database: NoteDatabase): TodoDao {
        return database.todoDao()
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): NoteDatabase {
        return Room.databaseBuilder(
            appContext,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }
}