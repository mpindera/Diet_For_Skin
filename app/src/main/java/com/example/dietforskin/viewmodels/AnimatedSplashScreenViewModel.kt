package com.example.dietforskin.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AnimatedSplashScreenViewModel : ViewModel() {
    var showBar by mutableStateOf(false)
        private set

    fun onShowBarChanged(showBar: Boolean) {
        this.showBar = showBar
    }
}