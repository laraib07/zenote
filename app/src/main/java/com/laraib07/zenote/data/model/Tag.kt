package com.laraib07.zenote.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Tag.TABLE_NAME)
data class Tag(
    val tagContent: String,
    @PrimaryKey(autoGenerate = true) val tagId: Long = 0L
) {
    companion object {
        const val TABLE_NAME = "tag_table"
    }
}
