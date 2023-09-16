package com.laraib07.zenote.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.laraib07.zenote.R
import com.laraib07.zenote.ui.ext.animatedComposable
import com.laraib07.zenote.ui.screen.archive.ArchivedScreen
import com.laraib07.zenote.ui.screen.deleted.DeletedScreen
import com.laraib07.zenote.ui.screen.notes.NotesScreen
import com.laraib07.zenote.ui.screen.tags.TagsScreen
import com.laraib07.zenote.ui.screen.todos.TodosScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NoteFolderNavGraph(
    noteFolderNavController: NavHostController,
    rootNavController: NavHostController,
    toggleDrawer: () -> Unit
) {

    AnimatedNavHost(
        navController = noteFolderNavController,
        startDestination = NoteFolderScreen.AllNotes.route
    ) {
        animatedComposable(route = NoteFolderScreen.AllNotes.route) {
            NotesScreen(
                onTapDrawer = toggleDrawer,
//                noteFolderNavController = noteFolderNavController,
                rootNavController = rootNavController
            )
        }
        animatedComposable(route = NoteFolderScreen.Archived.route) {
            ArchivedScreen(
                onTapDrawer = toggleDrawer,
                rootNavController = rootNavController
            )
        }
        animatedComposable(route = NoteFolderScreen.Deleted.route) {
            DeletedScreen(
                onTapDrawer = toggleDrawer,
                rootNavController = rootNavController
            )
        }
        animatedComposable(route = NoteFolderScreen.Tags.route) {
            TagsScreen(onTapDrawer = toggleDrawer)
        }
        animatedComposable(route = NoteFolderScreen.Todos.route) {
            TodosScreen(onTapDrawer = toggleDrawer)
        }
    }
}