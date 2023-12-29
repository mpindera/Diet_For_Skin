package com.example.dietforskin

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.example.dietforskin.pages.patient_information_view.UpdatePatientInformation
import com.example.dietforskin.pages.profile_view.ProfileView
import com.example.dietforskin.pages.splash_screen.AnimatedSplashScreen
import com.example.dietforskin.ui.theme.DietForSkinTheme
import com.example.dietforskin.viewmodels.AnimatedSplashScreenViewModel
import com.example.dietforskin.viewmodels.AuthManager
import com.example.dietforskin.viewmodels.ChatViewModel
import com.example.dietforskin.viewmodels.FavoritePostsViewModel
import com.example.dietforskin.viewmodels.MainViewModel
import com.example.dietforskin.viewmodels.ProfileViewModel
import com.example.dietforskin.viewmodels.UpdatePatientInformationViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()
    private val chatViewModel by viewModels<ChatViewModel>()
    private val profileViewModel by viewModels<ProfileViewModel>()
    private val animatedSplashScreenViewModel by viewModels<AnimatedSplashScreenViewModel>()
    private val favoritePostsViewModel by viewModels<FavoritePostsViewModel>()
    private val updatePatientInformationViewModel by viewModels<UpdatePatientInformationViewModel>()


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
                    lifecycleScope.launch {
                        authManager.login(
                            savedEmail,
                            savedPassword,
                            navController,
                            profileViewModel,
                            animatedSplashScreenViewModel
                        )
                    }
                } else {
                    profileViewModel.updateSelection(PagesToRoles.NOT_LOGGED)
                }

                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(topBar = {
                        if (animatedSplashScreenViewModel.showBar) {
                            TopBarView(
                                profileViewModel = profileViewModel,
                                navController = navController,
                                animatedSplashScreenViewModel = animatedSplashScreenViewModel
                            )
                        }

                    }, modifier = Modifier.fillMaxSize(), bottomBar = {
                        if (animatedSplashScreenViewModel.showBar) {
                            BottomBarView(
                                navController = navController, profileViewModel = profileViewModel
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
                                    navController = navController,
                                    animatedSplashScreenViewModel = animatedSplashScreenViewModel
                                )
                            }
                            composable(ScreensBottomBar.Home.route) {
                                MainViewOfPosts(
                                    mainViewModel = mainViewModel,
                                    profileViewModel = profileViewModel
                                )
                            }
                            composable(ScreensBottomBar.Favorite.route) {
                                FavoritePostsView(
                                    favoritePostsViewModel = favoritePostsViewModel,
                                    context = context,
                                    profileViewModel = profileViewModel
                                )
                            }
                            composable(ScreensBottomBar.AddPost.route) {
                                AddPostView(profileViewModel = profileViewModel)
                            }
                            composable(ScreensBottomBar.Profile.route) {
                                ProfileView(
                                    navController = navController,
                                    profileViewModel = profileViewModel,
                                    context = context,
                                    animatedSplashScreenViewModel = animatedSplashScreenViewModel
                                )
                            }
                            composable(ScreensBottomBar.CreateAccount.route) {
                                CreateAccount(
                                    navController = navController,
                                    context = context,
                                    profileViewModel = profileViewModel
                                )
                            }
                            composable(ScreensBottomBar.Chat.route) {
                                Chat(
                                    navController = navController,
                                    context = context,
                                    chatViewModel = chatViewModel,
                                    profileViewModel = profileViewModel
                                )
                            }
                            composable(Screen.PatientInformation.route) {
                                UpdatePatientInformation(
                                    profileViewModel = profileViewModel,
                                    context = context,
                                    updatePatientInformationViewModel=updatePatientInformationViewModel
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
