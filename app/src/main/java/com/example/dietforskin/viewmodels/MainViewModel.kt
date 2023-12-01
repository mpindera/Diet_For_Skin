package com.example.dietforskin.viewmodels

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.dietforskin.navigation.ScreensBottomBar
import com.example.dietforskin.data.auth.AuthRepository
import com.example.dietforskin.data.auth.PagesToRoles
import com.example.dietforskin.data.profile.PagesSite
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    var context: Context? by mutableStateOf(null)

    var selection by mutableStateOf(PagesToRoles.NOT_LOGGED)
        private set

    private var selectionOfPagesSite by mutableStateOf(PagesSite.MAIN_VIEW_POSTS)

    val isAdminLogged: Boolean
        get() = selection == PagesToRoles.ADMIN_LOGGED

    var showDialog by mutableStateOf(false)

    val isPatientLogged: Boolean
        get() = selection == PagesToRoles.PATIENT_LOGGED

    private val patientBottomBar = listOf(
        ScreensBottomBar.Home,
        ScreensBottomBar.Favorite,
        ScreensBottomBar.Chat
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

    var showBar by mutableStateOf(false)
        private set


    fun onShowBarChanged(showBar: Boolean) {
        this.showBar = showBar
    }

    fun selectedBottomBar(): List<ScreensBottomBar> {
        return if (isPatientLogged) {
            patientBottomBar
        } else if (isAdminLogged) {
            adminBottomBar
        } else {
            guestBottomBar
        }
    }

    private val _selectedScreen = mutableStateOf<ScreensBottomBar>(ScreensBottomBar.Home)
    val selectedScreen: State<ScreensBottomBar> = _selectedScreen

    fun updateSelectionOfPagesSite(newSelectionSite: PagesSite) {
        selectionOfPagesSite = newSelectionSite
    }

    fun updateSelection(newSelection: PagesToRoles) {
        selection = newSelection
    }

    fun updateSelectedScreen(newScreen: ScreensBottomBar) {
        _selectedScreen.value = newScreen
    }
    fun logout(mainViewModel: MainViewModel, authRepository: AuthRepository, context: Context) {
        mainViewModel.showDialog = false
        authRepository.logoutUser()
        mainViewModel.updateSelection(PagesToRoles.NOT_LOGGED)
        val sharedPreferences = context.getSharedPreferences("user_credentials", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}
