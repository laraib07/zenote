package com.laraib07.zenote.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.laraib07.zenote.R

sealed class NoteFolderScreen(
    val route: String,
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    @DrawableRes val iconFilled: Int
) {
    object AllNotes : NoteFolderScreen(
        "notes",
        R.string.all_notes_folder,
        R.drawable.ic_notes_folder,
        R.drawable.ic_notes_folder_filled
    )

    object Archived : NoteFolderScreen(
        "archived",
        R.string.archived_folder,
        R.drawable.ic_archived_folder,
        R.drawable.ic_archived_folder_filled
    )

    object Deleted : NoteFolderScreen(
        "deleted",
        R.string.deleted_folder,
        R.drawable.ic_deleted_folder,
        R.drawable.ic_deleted_folder_filled
    )

    object Tags : NoteFolderScreen(
        "tags",
        R.string.tags,
        R.drawable.ic_tags,
        R.drawable.ic_tags_filled
    )

    object Todos : NoteFolderScreen(
        "todos",
        R.string.todos,
        R.drawable.ic_checklist,
        R.drawable.ic_checklist
    )
}
