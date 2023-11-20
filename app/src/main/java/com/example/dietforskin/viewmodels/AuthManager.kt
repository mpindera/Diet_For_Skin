package com.example.dietforskin.viewmodels

import android.widget.Toast
import com.example.dietforskin.data.auth.AuthRepository
import com.example.dietforskin.data.auth.Resource
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

class AuthManager(private val authRepository: AuthRepository) {

    suspend fun login(email: String, password: String) {

        when (val result = authRepository.loginUser(email, password).firstOrNull()) {
            is Resource.Loading -> {
                result.message
            }

            is Resource.Success -> {
                result.data
            }

            is Resource.Error -> {
                result.message
            }

            else -> {

            }
        }
    }

    suspend fun register(email: String, password: String) {

        when (val result = authRepository.registerUser(email, password).firstOrNull()) {
            is Resource.Loading -> {
                result.message
            }

            is Resource.Success -> {
                result.data
            }

            is Resource.Error -> {
                result.message
            }

            else -> {

            }
        }
    }
}