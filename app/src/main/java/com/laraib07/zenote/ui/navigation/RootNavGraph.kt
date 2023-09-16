package com.laraib07.zenote.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.laraib07.zenote.ui.ext.animatedComposable
import com.laraib07.zenote.ui.screen.*
import com.laraib07.zenote.ui.screen.add_edit.AddEditNoteScreen
import com.laraib07.zenote.ui.screen.search.SearchScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RootNavGraph(
    onThemeChange: () -> Unit
) {
    val navController = rememberAnimatedNavController()

    AnimatedNavHost(
        navController = navController,
        startDestination = RootScreen.Main.route
    ) {
        animatedComposable(route = RootScreen.Main.route) {
            MainScreen(
                rootNavController = navController,
                onThemeChange = onThemeChange
            )
        }
        animatedComposable(
            route = RootScreen.AddEdit.route + "?noteId={noteId}",
            arguments = listOf(
                navArgument("noteId") {
                    type = NavType.LongType
                    defaultValue = 0L
                }
            )
        ) {
            AddEditNoteScreen(
                onBack = { navController.popBackStack() }
            )
        }

        animatedComposable(route = RootScreen.Search.route) {
            SearchScreen(
                onBackTap = { navController.popBackStack() },
                onNoteItemTap = { noteId ->
                    navController.navigate(
                        RootScreen.AddEdit.withNoteId(noteId)
                    )
                }
            )
        }
        animatedComposable(route = RootScreen.About.route) {
            AboutScreen(onBackTap = { navController.popBackStack() })
        }
        animatedComposable(route = RootScreen.Settings.route) {
            SettingsScreen(onBackTap = { navController.popBackStack() })
        }
    }
}