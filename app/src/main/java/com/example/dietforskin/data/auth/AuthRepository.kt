package com.example.dietforskin.data.auth

import android.content.Context
import com.example.dietforskin.viewmodels.MainViewModel
import com.google.firebase.auth.AuthResult

interface AuthRepository {
    suspend fun loginUser(email: String, password: String): Resource<AuthResult>
    suspend fun registerUser(email: String, password: String): Resource<AuthResult>
    fun logoutUser()
    suspend fun resetPassword(email: String)
}