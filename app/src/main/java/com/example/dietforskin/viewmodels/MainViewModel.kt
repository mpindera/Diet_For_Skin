package com.example.dietforskin.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.dietforskin.bars.bottombar.ScreensBottomBar
import com.example.dietforskin.data.auth.PagesToRoles
import com.example.dietforskin.data.profile.PagesSite
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {
  var selection by mutableStateOf(PagesToRoles.NOT_LOGGED)
  var selectionOfPagesSite by mutableStateOf(PagesSite.MAIN_VIEW_POSTS)

  private val adminLogged = selection == PagesToRoles.ADMIN_LOGGED
  private val userLogged = selection == PagesToRoles.PATIENT_LOGGED

  val patientBottomBar = listOf(
    ScreensBottomBar.Home,
    ScreensBottomBar.Favorite,
    ScreensBottomBar.Chat,
    ScreensBottomBar.Profile,
  )
  val adminBottomBar = listOf(
    ScreensBottomBar.Home,
    ScreensBottomBar.Favorite,
    ScreensBottomBar.Chat,
    ScreensBottomBar.AddPost,
    ScreensBottomBar.Profile,
    ScreensBottomBar.CreateAccount
  )
  val guestBottomBar = listOf(
    ScreensBottomBar.Home,
    ScreensBottomBar.Profile,
  )

  fun selectedBottomBar(): List<ScreensBottomBar> {
    return if (userLogged) {
      patientBottomBar
    } else if (adminLogged) {
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

}
