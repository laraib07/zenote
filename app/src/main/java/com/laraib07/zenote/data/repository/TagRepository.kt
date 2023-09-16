package com.laraib07.zenote.data.repository

import com.laraib07.zenote.data.dao.TagDao
import com.laraib07.zenote.data.model.Tag
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TagRepository @Inject constructor(
    private val tagDao: TagDao
) {

    fun getAllTags(): Flow<List<Tag>> {
        return tagDao.getAllTags()
    }

    suspend fun addAllTag(tags: List<Tag>) {
        tagDao.insertAllTag(tags)
    }

    suspend fun removeTag(tag: Tag) {
        tagDao.deleteTag(tag)
    }

    suspend fun getTagById(id: Long): Tag? = tagDao.getTagById(id)

    suspend fun tagAlreadyExists(label: String) = tagDao.alreadyExists(label)
}