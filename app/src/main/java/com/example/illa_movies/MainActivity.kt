package com.example.illa_movies

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.illa_movies.ui.favorites.FavoriteMoviesScreen
import com.example.illa_movies.ui.home.MoviesHomeScreen
import com.example.illa_movies.ui.theme.ILLAMoviesTheme
import dagger.hilt.android.AndroidEntryPoint


/**
 * This is the main activity of the ILLAMovies app, responsible for displaying the bottom navigation bar and
 * hosting the navigation graph for app screens.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    /**
     * The list of destinations used in the bottom navigation bar.
     */
    private val destinations = listOf(
        Screen.Home,
        Screen.Favorites,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Sets up the theme for the app.
            ILLAMoviesTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomNavigation {
                            // Initializes the navigation controller for the app.

                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination

                            // Loops through the list of destinations and adds each one to the bottom navigation bar.
                            destinations.forEach { screen ->

                                BottomNavigationItem(
                                    // Sets the icon and label for the destination.
                                    icon = { Icon(screen.icon, contentDescription = null) },
                                    label = { Text(stringResource(screen.resourceId)) },
                                    // Determines whether or not the current destination is selected.
                                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                    // Sets the action to perform when the destination is clicked.
                                    onClick = {
                                        navController.navigate(screen.route) {
                                            // Pop up to the start destination of the graph to
                                            // avoid building up a large stack of destinations
                                            // on the back stack as users select items
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            // Avoid multiple copies of the same destination when
                                            // reselecting the same item
                                            launchSingleTop = true
                                            // Restore state when reselecting a previously selected item
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    // Sets up the NavHost for the app.
                    NavHost(
                        navController,
                        startDestination = Screen.Home.route,
                        Modifier.padding(innerPadding)
                    ) {
                        // Adds the composable functions for each destination.
                        composable(Screen.Home.route) {
                            MoviesHomeScreen(
                                modifier = Modifier.fillMaxSize(),
                            )
                        }
                        composable(Screen.Favorites.route) {
                            FavoriteMoviesScreen(
                                modifier = Modifier.fillMaxSize(),
                            )
                        }
                    }
                }
            }
        }
    }
}

