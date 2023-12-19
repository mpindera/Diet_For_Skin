package com.example.dietforskin.viewmodels

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.dietforskin.navigation.ScreensBottomBar
import com.example.dietforskin.data.auth.AuthRepository
import com.example.dietforskin.data.auth.PagesToRoles
import com.example.dietforskin.data.profile.PagesSite
import com.example.dietforskin.data.profile.person.Patient
import com.example.dietforskin.navigation.Screen
import com.example.dietforskin.pages.CommonElements
import kotlinx.coroutines.tasks.await

class MainViewModel : ViewModel() {


}
