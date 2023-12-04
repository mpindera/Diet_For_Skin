package com.example.dietforskin

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dietforskin.bars.topbar.TopBarView
import com.example.dietforskin.pages.addpost_view.AddPostView
import com.example.dietforskin.bars.bottombar.BottomBarView
import com.example.dietforskin.navigation.ScreensBottomBar
import com.example.dietforskin.pages.create_account.CreateAccount
import com.example.dietforskin.data.auth.AuthRepositoryImpl
import com.example.dietforskin.data.auth.PagesToRoles
import com.example.dietforskin.navigation.Screen
import com.example.dietforskin.pages.chat_view.Chat
import com.example.dietforskin.pages.favorite_view.FavoritePostsView
import com.example.dietforskin.pages.main_view.MainViewOfPosts
import com.example.dietforskin.pages.profile_view.ProfileView
import com.example.dietforskin.pages.splash_screen.AnimatedSplashScreen
import com.example.dietforskin.ui.theme.DietForSkinTheme
import com.example.dietforskin.viewmodels.AuthManager
import com.example.dietforskin.viewmodels.MainViewModel
import com.google.firebase.auth.FirebaseAuth
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
                val authRepository =
                    AuthRepositoryImpl(firebaseAuth = FirebaseAuth.getInstance(), context = context)
                val authManager = AuthManager(authRepository, this)

                val sharedPreferences =
                    getSharedPreferences("user_credentials", Context.MODE_PRIVATE)
                val savedEmail = sharedPreferences.getString("email", null)
                val savedPassword = sharedPreferences.getString("password", null)
                var lastClickTime by remember { mutableStateOf(0L) }

                val doubleClickThreshold = 500

                if (savedEmail != null && savedPassword != null) {
                    Log.d("test5","jestem tutaj w logged")
                    lifecycleScope.launch {
                        authManager.login(savedEmail, savedPassword, navController, mainViewModel)
                    }
                } else {
                    Log.d("test5","jestem tutaj w notLogged")
                    mainViewModel.updateSelection(PagesToRoles.NOT_LOGGED)
                }

                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            if (mainViewModel.showBar) {
                                TopBarView(mainViewModel=mainViewModel,navController=navController)
                            }

                        }, modifier = Modifier.fillMaxSize(), bottomBar = {
                            if (mainViewModel.showBar) {
                                BottomBarView(
                                    navController = navController, mainViewModel = mainViewModel
                                )
                            }

                        }) { paddingValues ->
                        NavHost(
                            navController = navController,
                            startDestination = Screen.Splash.route,
                            modifier = Modifier.padding(paddingValues = paddingValues)
                        ) {
                            composable(Screen.Splash.route) {
                                AnimatedSplashScreen(
                                    navController = navController, mainViewModel = mainViewModel
                                )
                            }
                            composable(ScreensBottomBar.Home.route) {
                                MainViewOfPosts(mainViewModel)
                            }
                            composable(ScreensBottomBar.Favorite.route) {
                                FavoritePostsView(
                                    mainViewModel = mainViewModel, context = context
                                )
                            }
                            composable(ScreensBottomBar.AddPost.route) {
                                AddPostView()
                            }
                            composable(ScreensBottomBar.Profile.route) {
                                ProfileView(
                                    navController = navController,
                                    mainViewModel = mainViewModel,
                                    context = context
                                )
                            }
                            composable(ScreensBottomBar.CreateAccount.route) {
                                CreateAccount(
                                    navController = navController, context = context
                                )
                            }
                            composable(ScreensBottomBar.Chat.route) {
                                Chat(
                                    navController = navController, context = context
                                )
                            }
                        }
                        BackHandler {
                            val currentTime = System.currentTimeMillis()
                            if (currentTime - lastClickTime < doubleClickThreshold) {
                                finish()
                            }
                            lastClickTime = currentTime
                        }
                    }
                }
            }
        }
    }
}
