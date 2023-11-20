package com.example.dietforskin.data.auth

import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun loginUser(email: String, password: String): Flow<Resource<AuthRepositoryImpl>>
    fun registerUser(email: String, password: String): Flow<Resource<AuthRepositoryImpl>>
}