package com.laraib07.zenote.ui.screen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.laraib07.zenote.ui.screen.common.ZenoteHeader
import com.laraib07.zenote.ui.navigation.NoteFolderNavGraph
import com.laraib07.zenote.ui.navigation.NoteFolderScreen
import com.laraib07.zenote.ui.navigation.RootScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen(
    rootNavController: NavHostController,
    onThemeChange: () -> Unit,
    modifier: Modifier = Modifier
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberAnimatedNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination


    val noteFolderItems = listOf(
        NoteFolderScreen.AllNotes,
        NoteFolderScreen.Archived,
        NoteFolderScreen.Deleted,
        NoteFolderScreen.Tags,
        NoteFolderScreen.Todos
    )

    val rootItems = listOf(
        RootScreen.Settings,
        RootScreen.About
    )

    ModalNavigationDrawer(
        modifier = modifier,
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
//                Text(
//                    text = stringResource(id = R.string.app_name),
//                    color = MaterialTheme.colorScheme.surfaceTint,
//                    style = MaterialTheme.typography.headlineMedium,
//                    modifier = Modifier
//                        .padding(16.dp)
//                        .clickable { onThemeChange() }
//                )
                ZenoteHeader(onTap = onThemeChange)
                noteFolderItems.forEach { item ->

                    val selected = currentDestination?.hierarchy?.any {
                        it.route == item.route
                    } == true

                    NavigationDrawerItem(
                        icon = {
                            Icon(
                                painter = painterResource(
                                    id = if (selected) item.iconFilled else item.icon
                                ),
                                modifier = Modifier.size(24.dp),
                                contentDescription = stringResource(id = item.title),
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(id = item.title),
                                style = MaterialTheme.typography.labelLarge
                            )
                        },
                        selected = selected,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                                if (!selected) {
                                    navController.navigate(item.route) {
                                        // Avoid multiple copies of the same destination when
                                        // re-selecting the same item
                                        launchSingleTop = true
                                        // Pop up to the start destination of the graph to
                                        // avoid building up a large stack of destinations
                                        // on the back stack as users select items
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }

                                        // Restore state when re-selecting a previously selected item
                                        restoreState = true
                                    }
                                }

                            }
                        },
                        modifier = Modifier
                            .padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                    if (item == NoteFolderScreen.Deleted || item == NoteFolderScreen.Tags) {
                        Divider(modifier = Modifier.padding(vertical = 8.dp))
                    }
                }

                rootItems.forEach { item ->
                    NavigationDrawerItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = item.icon ?: 0),
                                contentDescription = stringResource(id = item.title)
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(id = item.title),
                                style = MaterialTheme.typography.labelLarge
                            )
                        },
                        selected = false,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                                rootNavController.navigate(item.route) {
                                    launchSingleTop = true
                                }
                            }

                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        content = {
            NoteFolderNavGraph(
                noteFolderNavController = navController,
                rootNavController = rootNavController,
                toggleDrawer = {
                    scope.launch {
                        if (drawerState.isClosed)
                            drawerState.open()
                        else
                            drawerState.close()
                    }
                }
            )
        }
    )
}
