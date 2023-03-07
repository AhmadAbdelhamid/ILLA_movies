package com.example.illa_movies

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object Home : Screen("profile", R.string.title_home, Icons.Filled.Home)
    object Favorites : Screen("friendslist", R.string.title_favorites, Icons.Filled.Favorite)
}
