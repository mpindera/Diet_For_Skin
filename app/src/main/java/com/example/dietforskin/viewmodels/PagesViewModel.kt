package com.example.dietforskin.viewmodels


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PagesViewModel : ViewModel() {
    /** Username section in Create Account **/

    private var _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    fun onUsernameChanged(username: String) {
        _username.value = username
    }

    private var _selectedRole = MutableStateFlow("ROLE")
    val selectedRole: StateFlow<String> = _selectedRole
    fun onSelectedRoleChanged(selectedRole: String) {
        _selectedRole.value = selectedRole
    }

    /** Common Email section in Create Account and Profile View **/

    private var _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    fun onEmailChanged(email: String) {
        _email.value = email.lowercase()
    }

    private var _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    fun onPasswordChanged(password: String) {
        _password.value = password
    }



}