package com.example.dietforskin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dietforskin.addpostview.AddPostView
import com.example.dietforskin.bottombar.BottomBarView
import com.example.dietforskin.bottombar.ScreensBottomBar
import com.example.dietforskin.favoriteview.FavoritePostsView
import com.example.dietforskin.pages.MainViewOfPosts
import com.example.dietforskin.profileview.ProfileView
import com.example.dietforskin.topbar.TopBarView
import com.example.dietforskin.ui.theme.DietForSkinTheme
import com.example.dietforskin.viewmodels.MainViewModel

class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            DietForSkinTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            TopBarView()
                        },
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = {
                            BottomBarView(navController = navController)
                        }
                    ) { paddingValues ->
                        NavHost(
                            navController = navController,
                            startDestination = ScreensBottomBar.Home.route,
                            modifier = Modifier.padding(paddingValues = paddingValues)
                        ) {
                            composable(ScreensBottomBar.Home.route) {
                                MainViewOfPosts()
                            }
                            composable(ScreensBottomBar.Favorite.route) {
                                FavoritePostsView()
                            }
                            composable(ScreensBottomBar.AddPost.route) {
                                AddPostView()
                            }
                            composable(ScreensBottomBar.Profile.route) {
                                ProfileView()
                            }
                        }
                    }
                }
            }
        }
    }
}