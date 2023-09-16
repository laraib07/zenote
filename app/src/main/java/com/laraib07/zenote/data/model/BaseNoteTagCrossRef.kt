package com.laraib07.zenote.data.model

import androidx.room.Entity
import androidx.room.Index

@Entity(
    primaryKeys = ["noteId", "tagId"],
    indices = [Index("noteId"), Index("tagId")]
)
data class BaseNoteTagCrossRef(
    val noteId: Long,
    val tagId: Long
)
