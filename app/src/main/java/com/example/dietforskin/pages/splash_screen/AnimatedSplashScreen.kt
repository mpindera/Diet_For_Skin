package com.example.dietforskin.pages.splash_screen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.dietforskin.navigation.ScreensBottomBar
import com.example.dietforskin.ui.theme.colorCardIngredient
import com.example.dietforskin.ui.theme.fontFamilyTitle
import com.example.dietforskin.viewmodels.AnimatedSplashScreenViewModel
import com.example.dietforskin.viewmodels.MainViewModel
import kotlinx.coroutines.delay

@Composable
fun AnimatedSplashScreen(navController: NavHostController, animatedSplashScreenViewModel: AnimatedSplashScreenViewModel) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(colorCardIngredient)
    ) {
        val visible by remember { mutableStateOf(true) }

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(initialAlpha = 0.4f),
            exit = fadeOut(animationSpec = tween(durationMillis = 2000))
        ){
            Text(
                "Diet For Skin", fontFamily = fontFamilyTitle,
                letterSpacing = 1.sp
            )
        }

        LaunchedEffect(key1 = true) {
            delay(2000)

            animatedSplashScreenViewModel.onShowBarChanged(true)
            navController.navigate(ScreensBottomBar.Home.route)
        }
    }
}