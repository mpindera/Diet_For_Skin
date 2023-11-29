package com.example.dietforskin.data.auth

import android.content.Context
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.example.dietforskin.report.Reports
import com.example.dietforskin.viewmodels.MainViewModel
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(private val firebaseAuth: FirebaseAuth,private val context: Context) : AuthRepository {
  override suspend fun loginUser(email: String, password: String): Resource<AuthResult> {
    return try {
      val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
      Resource.Success(authResult)
    } catch (e: Exception) {
      Resource.Error("Login failed: ${e.message}")
    }
  }

  override suspend fun registerUser(email: String, password: String): Resource<AuthResult> {
    return try {
      val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
      Resource.Success(authResult)
    } catch (e: Exception) {
      Resource.Error("Registration failed: ${e.message}")
    }
  }

  override fun logoutUser() {
    FirebaseAuth.getInstance().signOut()
  }

  override suspend fun resetPassword(email: String) {
    FirebaseAuth.getInstance().sendPasswordResetEmail(email)
  }



}