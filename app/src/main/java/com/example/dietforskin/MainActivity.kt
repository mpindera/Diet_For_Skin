package com.example.dietforskin

import android.content.Context
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dietforskin.addpostview.AddPostView
import com.example.dietforskin.bottombar.BottomBarView
import com.example.dietforskin.bottombar.ScreensBottomBar
import com.example.dietforskin.create_account.CreateAccount
import com.example.dietforskin.data.auth.AuthRepositoryImpl
import com.example.dietforskin.data.auth.PagesToRoles
import com.example.dietforskin.favoriteview.FavoritePostsView
import com.example.dietforskin.pages.MainViewOfPosts
import com.example.dietforskin.profileview.ProfileView
import com.example.dietforskin.topbar.TopBarView
import com.example.dietforskin.ui.theme.DietForSkinTheme
import com.example.dietforskin.viewmodels.AuthManager
import com.example.dietforskin.viewmodels.MainViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val context = LocalContext.current
            DietForSkinTheme {
                val authRepository = AuthRepositoryImpl()
                val authManager = AuthManager(authRepository, this)

                val sharedPreferences =
                    getSharedPreferences("user_credentials", Context.MODE_PRIVATE)
                val savedEmail = sharedPreferences.getString("email", null)
                val savedPassword = sharedPreferences.getString("password", null)

                if (savedEmail != null && savedPassword != null) {
                    lifecycleScope.launch {
                        authManager.login(savedEmail, savedPassword, navController, mainViewModel)
                    }
                } else {
                    mainViewModel.updateSelection(PagesToRoles.NOT_LOGGED)
                }

                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            TopBarView()
                        },
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = {
                            BottomBarView(
                                navController = navController,
                                mainViewModel = mainViewModel
                            )
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
                                FavoritePostsView(mainViewModel = mainViewModel, context = context)
                            }
                            composable(ScreensBottomBar.AddPost.route) {
                                AddPostView()
                            }
                            composable(ScreensBottomBar.Profile.route) {
                                ProfileView(
                                    navController = navController,
                                    mainViewModel = mainViewModel
                                )
                            }
                            composable(ScreensBottomBar.CreateAccount.route) {
                                CreateAccount(
                                    mainViewModel = mainViewModel,
                                    navController = navController
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}