package com.example.dietforskin.pages.splash_screen

import android.annotation.SuppressLint
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.dietforskin.navigation.ScreensBottomBar
import com.example.dietforskin.ui.theme.colorCardIngredient
import com.example.dietforskin.ui.theme.fontFamilyTitle
import com.example.dietforskin.viewmodels.MainViewModel
import kotlinx.coroutines.delay

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Composable
fun AnimatedSplashScreen(navController: NavHostController, mainViewModel: MainViewModel) {
    val scale = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                })
        )
        delay(500)
        mainViewModel.onShowBarChanged(true)
        navController.navigate(ScreensBottomBar.Home.route)
    }
    Splash(scale)

}

@Composable
fun Splash(scale: Animatable<Float, AnimationVector1D>) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(colorCardIngredient)
    ) {
        Text(
            "Diet For Skin", fontFamily = fontFamilyTitle,
            letterSpacing = 1.sp, modifier = Modifier.scale(scale.value)
        )
    }
}