package com.example.dietforskin.bottombar

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.dietforskin.data.profile.guest.Guest

@Composable
fun BottomBarView(navController: NavHostController) {
    NavigationBar {
        val items = listOf(
            ScreensBottomBar.Home,
            ScreensBottomBar.Favorite,
            ScreensBottomBar.AddPost,
            ScreensBottomBar.Profile
        )

        var selectedScreen by remember { mutableStateOf(items.first()) }
        items.forEach { screen ->
            val selected = selectedScreen == screen
            NavigationBarItem(selected = selected, onClick = {
                selectedScreen = screen
                navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }, label = { Text(screen.label) }, icon = {

                val vector = if (selected) screen.icon else screen.outlinedIcon ?: screen.icon

                AnimatedContent(targetState = vector, label = "vector", transitionSpec = {
                    fadeIn(animationSpec = tween(250)).togetherWith(
                        fadeOut(
                            animationSpec = tween(
                                250
                            )
                        )
                    )
                }) {
                    if (screen == ScreensBottomBar.Favorite) {
                        Icon(
                            imageVector = it,
                            contentDescription = screen.label,
                            tint = Color(0xFFE07575)
                        )
                    } else {
                        Icon(
                            imageVector = it, contentDescription = screen.label
                        )
                    }
                }

            })
        }
    }
}
