package com.example.dietforskin.bottombar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ScreensBottomBar(
    val route: String,
    val label: String,
    var icon: ImageVector,
    var outlinedIcon: ImageVector? = null
) {
    object Home : ScreensBottomBar(
        route = "home",
        label = "Home",
        icon = Icons.Filled.Home,
        outlinedIcon = Icons.Outlined.Home
    )
    object Favorite : ScreensBottomBar(
        route = "favorite",
        label = "Favorite",
        icon = Icons.Filled.Favorite,
        outlinedIcon = Icons.Outlined.FavoriteBorder
    )

    object AddPost : ScreensBottomBar(
        route = "addPost",
        label = "Add Post",
        icon = Icons.Filled.AddCircle,
        outlinedIcon = Icons.Outlined.Add
    )

    object Profile : ScreensBottomBar(
        route = "profile",
        label = "Profile",
        icon = Icons.Filled.Person,
        outlinedIcon = Icons.Outlined.Person
    )
}
