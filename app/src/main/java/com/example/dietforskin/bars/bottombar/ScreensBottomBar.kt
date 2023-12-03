package com.example.dietforskin.bars.bottombar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ScreensBottomBar(
    val route: String,
    val label: String,
    var filledIcon: ImageVector,
    var outlinedIcon: ImageVector? = null
) {
    object Home : ScreensBottomBar(
        route = "home",
        label = "Home",
        filledIcon = Icons.Filled.Home,
        outlinedIcon = Icons.Outlined.Home
    )

    object Favorite : ScreensBottomBar(
        route = "favorite",
        label = "Favorite",
        filledIcon = Icons.Filled.Favorite,
        outlinedIcon = Icons.Outlined.FavoriteBorder
    )

    object AddPost : ScreensBottomBar(
        route = "addPost",
        label = "Add Post",
        filledIcon = Icons.Filled.AddCircle,
        outlinedIcon = Icons.Outlined.Add
    )

    object Profile : ScreensBottomBar(
        route = "profile",
        label = "Profile",
        filledIcon = Icons.Filled.Person,
        outlinedIcon = Icons.Outlined.Person
    )

    object CreateAccount : ScreensBottomBar(
        route = "createAccount",
        label = "Create",
        filledIcon = Icons.Filled.AccountBox,
        outlinedIcon = Icons.Outlined.AccountBox
    )

    object Chat : ScreensBottomBar(
        route = "chat",
        label= "Chat",
        filledIcon = Icons.Filled.Create,
        outlinedIcon = Icons.Outlined.Create
    )
}
