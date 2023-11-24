package com.example.dietforskin.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.dietforskin.bottombar.ScreensBottomBar
import com.example.dietforskin.data.auth.PagesToRoles

class MainViewModel : ViewModel() {
    var selection by mutableStateOf(PagesToRoles.NOT_LOGGED)
    fun updateSelection(newSelection: PagesToRoles) {
        selection = newSelection
    }


    private val _selectedScreen = mutableStateOf<ScreensBottomBar>(ScreensBottomBar.Home)
    val selectedScreen: State<ScreensBottomBar> = _selectedScreen

    fun updateSelectedScreen(newScreen: ScreensBottomBar) {
        _selectedScreen.value = newScreen
    }
}
