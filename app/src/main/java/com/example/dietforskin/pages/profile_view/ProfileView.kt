package com.example.dietforskin.pages.profile_view

import android.content.Context
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.dietforskin.R
import com.example.dietforskin.data.auth.AuthRepository
import com.example.dietforskin.data.auth.AuthRepositoryImpl
import com.example.dietforskin.data.profile.PagesSite
import com.example.dietforskin.pages.CommonElements
import com.example.dietforskin.viewmodels.AnimatedSplashScreenViewModel
import com.example.dietforskin.viewmodels.AuthManager
import com.example.dietforskin.viewmodels.PagesViewModel
import com.example.dietforskin.viewmodels.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth

/** Profile is a activity where everyone has to write mail, password to join into panel.
Everyone who forgot password can click into "Forgot Password". After that you have to enter your
email. Then, open email to change password. Every new Patient have to click and change password.

 **/
@Composable
fun ProfileView(
    navController: NavHostController,
    context: Context,
    profileViewModel: ProfileViewModel,
    animatedSplashScreenViewModel: AnimatedSplashScreenViewModel
) {
    profileViewModel.updateSelectionOfPagesSite(PagesSite.PROFILE_VIEW)

    val pagesViewModel = remember { PagesViewModel() }
    val email by pagesViewModel.email.collectAsState()
    val password by pagesViewModel.password.collectAsState()

    val authRepository: AuthRepository =
        AuthRepositoryImpl(firebaseAuth = FirebaseAuth.getInstance(), context = context)
    val authManager = AuthManager(authRepository, context)
    val coroutineScope = rememberCoroutineScope()

    var visibilityOfPassword by remember {
        mutableStateOf(true)
    }
    var visibilityOfForgotPassword by remember {
        mutableStateOf(false)
    }

    val icon = if (visibilityOfPassword) {
        painterResource(id = R.drawable.baseline_visibility_24)
    } else {
        painterResource(id = R.drawable.baseline_visibility_off_24)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding()
    ) {
        CommonElements().CanvasBackground(modifier = Modifier.align(alignment = Alignment.TopEnd))
        CommonElements().canvasWithName(stringResource(id = R.string.profile))

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(start = 40.dp, end = 40.dp)
        ) {
            AnimatedContent(targetState = visibilityOfForgotPassword,
                label = "vector",
                transitionSpec = {
                    fadeIn(animationSpec = tween(250)).togetherWith(
                        fadeOut(
                            animationSpec = tween(
                                250
                            )
                        )
                    )
                }) { targetState ->
                if (targetState) {
                    ForgotPasswordForm(email = email,
                        authManager = authManager,
                        onValueChangeEmail = pagesViewModel::onEmailChanged,
                        coroutineScope = coroutineScope,
                        pagesViewModel = pagesViewModel,
                        backToLoginText = {
                            Text(
                                modifier = Modifier
                                    .padding(top = 5.dp)
                                    .clickable {
                                        visibilityOfForgotPassword = false
                                    },
                                text = stringResource(id = R.string.back_to_login),
                                fontStyle = FontStyle.Italic
                            )
                        })
                } else {
                    LoginForm(email = email.lowercase(),
                        password = password,
                        visualTransformation = if (visibilityOfPassword) {
                            PasswordVisualTransformation()
                        } else {
                            VisualTransformation.None
                        },
                        animatedSplashScreenViewModel = animatedSplashScreenViewModel,
                        coroutineScope = coroutineScope,
                        authManager = authManager,
                        navController = navController,
                        profileViewModel = profileViewModel,
                        onValueChangeEmail = pagesViewModel::onEmailChanged,
                        onValueChangePassword = pagesViewModel::onPasswordChanged,
                        trailingIcon = {
                            IconButton(onClick = { visibilityOfPassword = !visibilityOfPassword }) {
                                Icon(
                                    icon, contentDescription = "Show Icon"
                                )
                            }
                        },
                        forgetText = {
                            Text(
                                modifier = Modifier
                                    .padding(top = 5.dp)
                                    .clickable {
                                        visibilityOfForgotPassword = true
                                    },
                                text = stringResource(id = R.string.reset_Password),
                                fontStyle = FontStyle.Italic
                            )
                        })

                }
            }
        }
    }
}

