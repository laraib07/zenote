package com.laraib07.zenote.data.dao

import androidx.room.*
import com.laraib07.zenote.data.model.Tag
import kotlinx.coroutines.flow.Flow

private const val TABLE = Tag.TABLE_NAME

@Dao
interface TagDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTag(tags: List<Tag>)

    @Delete
    suspend fun deleteTag(tag: Tag)

    @Query("SELECT * FROM $TABLE WHERE tagId=:id")
    suspend fun getTagById(id: Long): Tag?

    @Query("SELECT * FROM $TABLE ORDER BY tagContent ASC")
    fun getAllTags(): Flow<List<Tag>>

    @Query("SELECT EXISTS(SELECT 1 FROM $TABLE WHERE tagContent=:label)")
    suspend fun alreadyExists(label: String): Boolean

}