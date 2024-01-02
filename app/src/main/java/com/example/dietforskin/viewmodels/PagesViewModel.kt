package com.example.dietforskin.viewmodels


import androidx.compose.ui.Alignment
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PagesViewModel : ViewModel() {
    /** Username section in Create Account **/

    private var _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    fun onNameChanged(name: String) {
        _name.value = name
    }

    private var _surname = MutableStateFlow("")
    val surname: StateFlow<String> = _surname

    fun onSurnameChanged(surname: String) {
        _surname.value = surname
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

    fun clearFields() {
        _name.value = ""
        _surname.value = ""
        _email.value = ""
        _password.value = ""
        _selectedRole.value = "ROLE"
        _selectedDietitian.value = "DIETITIAN"
    }

    fun clearFieldsOfForgotPassword() {
        _email.value = ""
    }


}