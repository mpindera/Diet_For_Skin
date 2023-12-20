package com.example.dietforskin.viewmodels


import androidx.compose.ui.Alignment
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

    private var _selectedDietitian = MutableStateFlow("DIETITIAN")
    val selectedDietitian: StateFlow<String> = _selectedDietitian
    fun onSelectedDietitianChanged(selectedDietitian: String) {
        _selectedDietitian.value = selectedDietitian
    }

    private val _visibilityOfAnimation = MutableStateFlow(false)
    val visibilityOfAnimation: StateFlow<Boolean> = _visibilityOfAnimation

    fun onVisibilityChanged(visibility: Boolean) {
        _visibilityOfAnimation.value = visibility
    }

    fun modif(visibilityOfAnimation: StateFlow<Boolean>): Alignment{
        return if(visibilityOfAnimation.value){
            Alignment.BottomStart
        }else{
            Alignment.Center
        }
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

    private var _passwordAdmin = MutableStateFlow("")
    val passwordAdmin: StateFlow<String> = _passwordAdmin

    fun onPasswordAdminChanged(passwordAdmin: String) {
        _passwordAdmin.value = passwordAdmin
    }

    fun clearFields() {
        _username.value = ""
        _email.value = ""
        _password.value = ""
        _selectedRole.value = "ROLE"
        _selectedDietitian.value = "DIETITIAN"
    }


}