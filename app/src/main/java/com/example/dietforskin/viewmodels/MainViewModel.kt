package com.example.dietforskin.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.dietforskin.data.auth.PagesToRoles

class MainViewModel : ViewModel() {
    var selection by mutableStateOf(PagesToRoles.NOT_LOGGED)
    fun updateSelection(newSelection: PagesToRoles) {
        selection = newSelection
    }

}
