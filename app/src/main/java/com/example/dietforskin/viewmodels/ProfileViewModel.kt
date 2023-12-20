package com.example.dietforskin.viewmodels

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.dietforskin.data.auth.AuthRepository
import com.example.dietforskin.data.auth.PagesToRoles
import com.example.dietforskin.data.profile.PagesSite
import com.example.dietforskin.navigation.Screen
import com.example.dietforskin.navigation.ScreensBottomBar

class ProfileViewModel : ViewModel() {
    private var selectionOfPagesSite by mutableStateOf(PagesSite.MAIN_VIEW_POSTS)
    private val _selectedScreen = mutableStateOf<ScreensBottomBar>(ScreensBottomBar.Home)
    var selection by mutableStateOf(PagesToRoles.NOT_LOGGED)
        private set


    val selectedScreen: State<ScreensBottomBar> = _selectedScreen

    val isAdminLogged: Boolean
        get() = selection == PagesToRoles.ADMIN_LOGGED

    var showDialog by mutableStateOf(false)

    val isPatientLogged: Boolean
        get() = selection == PagesToRoles.PATIENT_LOGGED

    fun updateSelectionOfPagesSite(newSelectionSite: PagesSite) {
        selectionOfPagesSite = newSelectionSite
    }

    fun updateSelection(newSelection: PagesToRoles) {
        selection = newSelection
    }

    fun updateSelectedScreen(newScreen: ScreensBottomBar) {
        _selectedScreen.value = newScreen
    }

    private val patientBottomBar = listOf(
        ScreensBottomBar.Home, ScreensBottomBar.Favorite, ScreensBottomBar.Chat
    )
    private val adminBottomBar = listOf(
        ScreensBottomBar.Home,
        ScreensBottomBar.Favorite,
        ScreensBottomBar.AddPost,
        ScreensBottomBar.Chat,
        ScreensBottomBar.CreateAccount
    )
    private val guestBottomBar = listOf(
        ScreensBottomBar.Home,
        ScreensBottomBar.Profile,
    )


    fun selectedBottomBar(): List<ScreensBottomBar> {
        return if (isPatientLogged) {
            patientBottomBar
        } else if (isAdminLogged) {
            adminBottomBar
        } else {
            guestBottomBar
        }
    }

    fun logout(
        profileViewModel: ProfileViewModel,
        authRepository: AuthRepository,
        context: Context,
        navController: NavHostController
    ) {
        try {
            profileViewModel.showDialog = false
            navController.navigate(Screen.Splash.route) {
                popUpTo(Screen.Splash.route) { inclusive = true }
            }
            authRepository.logoutUser()
            profileViewModel.updateSelection(PagesToRoles.NOT_LOGGED)
            val sharedPreferences =
                context.getSharedPreferences("user_credentials", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
        } catch (e: Exception) {
            e.toString()
        }
    }

}