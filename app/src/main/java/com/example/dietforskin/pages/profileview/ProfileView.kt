package com.example.dietforskin.pages.profileview

import android.content.Context
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.dietforskin.R
import com.example.dietforskin.data.auth.AuthRepository
import com.example.dietforskin.data.auth.AuthRepositoryImpl
import com.example.dietforskin.data.auth.PagesToRoles
import com.example.dietforskin.data.profile.PagesSite
import com.example.dietforskin.elements.CustomTextField
import com.example.dietforskin.pages.CommonElements
import com.example.dietforskin.ui.theme.colorCircle
import com.example.dietforskin.viewmodels.AuthManager
import com.example.dietforskin.viewmodels.MainViewModel
import com.example.dietforskin.viewmodels.PagesViewModel

@Composable
fun ProfileView(navController: NavHostController, mainViewModel: MainViewModel, context: Context) {
    mainViewModel.updateSelectionOfPagesSite(PagesSite.PROFILE_VIEW)
    val notLogged = mainViewModel.selection == PagesToRoles.NOT_LOGGED

    val pagesViewModel = remember { PagesViewModel() }
    val email by pagesViewModel.email.collectAsState()
    val password by pagesViewModel.password.collectAsState()

    val authRepository: AuthRepository = AuthRepositoryImpl()
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
        Spacer(modifier = Modifier.border(1.dp, Color.Black))
        Canvas(modifier = Modifier.align(alignment = Alignment.TopEnd), onDraw = {
            drawCircle(
                color = colorCircle, radius = 450.dp.toPx()
            )
        })
        CommonElements().canvasWithName("PROFILE")

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
                        backToLoginText = {
                            Text(
                                modifier = Modifier
                                    .padding(top = 5.dp)
                                    .clickable {
                                        visibilityOfForgotPassword = false
                                    }, text = "Cofnij do logowania", fontStyle = FontStyle.Italic
                            )
                        })
                } else {
                    LoginForm(email = email,
                        authRepository = authRepository,
                        password = password,
                        visualTransformation = if (visibilityOfPassword) {
                            PasswordVisualTransformation()
                        } else {
                            VisualTransformation.None
                        },
                        coroutineScope = coroutineScope,
                        context = context,
                        authManager = authManager,
                        navController = navController,
                        mainViewModel = mainViewModel,
                        notLogged = notLogged,
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
                                    }, text = "Resetuj hasło", fontStyle = FontStyle.Italic
                            )
                        })
                }
            }
        }
    }
}

