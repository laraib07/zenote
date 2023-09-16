package com.laraib07.zenote.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.laraib07.zenote.R

sealed class RootScreen(
    val route: String,
    @StringRes val title: Int,
    @DrawableRes val icon: Int? = null,
) {
    object Main : RootScreen(
        "main",
        R.string.main_screen
    )

    object AddEdit : RootScreen(
        "add_edit",
        R.string.add_or_edit_screen,
    )

    object Search : RootScreen(
        "search",
        R.string.search
    )

    object Settings : RootScreen(
        "settings",
        R.string.settings,
        R.drawable.ic_settings,
    )

    object About : RootScreen(
        "about",
        R.string.about,
        R.drawable.ic_about
    )

    fun withNoteId(id: Long): String {
        return route + "?noteId=${id}"
    }
}