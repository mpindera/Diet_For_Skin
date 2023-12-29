package com.example.dietforskin.viewmodels

import android.content.Context
import android.util.Log
import androidx.navigation.NavHostController
import com.example.dietforskin.navigation.ScreensBottomBar
import com.example.dietforskin.data.auth.AuthRepository
import com.example.dietforskin.data.auth.PagesToRoles
import com.example.dietforskin.data.auth.Resource
import com.example.dietforskin.navigation.Screen
import com.example.dietforskin.pages.CommonElements
import com.example.dietforskin.report.Reports
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await


class AuthManager(private val authRepository: AuthRepository, private val context: Context) {

  suspend fun login(
    email: String,
    password: String,
    navController: NavHostController,
    profileViewModel: ProfileViewModel,
    animatedSplashScreenViewModel: AnimatedSplashScreenViewModel,
  ) {
    when (authRepository.loginUser(email, password)) {
      is Resource.Success -> {
        try {
          animatedSplashScreenViewModel.onShowBarChanged(false)
          CommonElements().mAuth.currentUser?.let {
            val role = getUserRoleFromSharedPreferences()
            profileViewModel.updateSelection(role)
            saveUserCredentials(email, password, role)
          }
          CommonElements().db.collection("users").get()
            .addOnSuccessListener { documents ->
              for (document in documents) {
                val userData = document.data
                val role = userData["role"].toString()
                val userEmail = userData["email"].toString()
                val name = userData["name"].toString()

                if (userEmail == email) {
                  check(
                    role = role,
                    name = name,
                    profileViewModel = profileViewModel,
                    context = context
                  )
                }
              }
              profileViewModel.updateSelectedScreen(ScreensBottomBar.Home)
              navController.navigate(Screen.Splash.route) {
                popUpTo(Screen.Splash.route) { inclusive = true }
              }

            }.addOnFailureListener {
              Reports(context = context).errorFetchFromDatabase()
            }
        } catch (e: Exception) {
          Log.e("Error Login", "Error Login", e)
        }
      }

      is Resource.Error -> {
        try {
          navController.navigate(ScreensBottomBar.Profile.route)
          Reports(context = context).errorEmailDoesNotExists()
        } catch (e: Exception) {
          Log.e("Error", "Error", e)
        }
      }
    }
  }

  suspend fun resetPasswordPatient(
    email: String
  ) = coroutineScope {
    try {
      val documents = CommonElements().db.collection("users").get().await()
      var foundMatch = false

      for (document in documents) {
        val userData = document.data
        val userEmail = userData["email"].toString()

        if (email == userEmail) {
          foundMatch = true
        }
      }

      if (foundMatch) {
        authRepository.resetPassword(email = email)
      } else {
        Reports(context = context).errorEmailDoesNotExists()
      }
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  private fun saveUserCredentials(email: String, password: String, role: PagesToRoles) {
    val sharedPreferences =
      context.getSharedPreferences("user_credentials", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("email", email)
    editor.putString("password", password)
    editor.putString("role", role.name)
    editor.apply()
  }

  private fun getUserRoleFromSharedPreferences(): PagesToRoles {
    val sharedPreferences =
      context.getSharedPreferences("user_credentials", Context.MODE_PRIVATE)
    val roleString = sharedPreferences.getString("role", PagesToRoles.NOT_LOGGED.name)
    return PagesToRoles.valueOf(roleString ?: PagesToRoles.NOT_LOGGED.name)
  }

  suspend fun register(email: String, password: String) {
    try {
      when (authRepository.registerUser(email, password)) {
        is Resource.Success -> {
          Reports(context).registerPerson()
        }

        is Resource.Error -> {
          Reports(context).errorRegisterPerson()
        }
      }
    } catch (e: Exception) {
      Log.e("Error Register", "Error Register", e)
    }
  }


  fun checkingAllFields(email: String, password: String): Boolean {
    return email.isNotEmpty() && password.isNotEmpty()
  }

  fun checkingAllFields(email: String): Boolean {
    return email.isNotEmpty()
  }

  fun checkingAllFields(
    name: String,
    surname: String,
    email: String,
    role: String,
    uuid: String
  ): Boolean {
    return name.isNotEmpty() && surname.isNotEmpty() && email.isNotEmpty() && role != "ROLE" && uuid.isNotEmpty()
  }

}

fun check(
  role: String,
  profileViewModel: ProfileViewModel,
  context: Context,
  name: String,
) {
  if (role == "Patient") {
    Reports(context).loggedSuccess(name = name)
    profileViewModel.updateSelection(PagesToRoles.PATIENT_LOGGED)
  }
  if (role == "Admin") {
    Reports(context).loggedSuccess(name = name)
    profileViewModel.updateSelection(PagesToRoles.ADMIN_LOGGED)
  }
}
