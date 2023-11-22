package com.example.dietforskin.viewmodels

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.navigation.NavHostController
import com.example.dietforskin.bottombar.ScreensBottomBar
import com.example.dietforskin.data.auth.AuthRepository
import com.example.dietforskin.data.auth.PagesToRoles
import com.example.dietforskin.data.auth.Resource
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore


class AuthManager(private val authRepository: AuthRepository, private val context: Context) {
    private val db = Firebase.firestore
    private val mAuth = FirebaseAuth.getInstance()

    suspend fun login(
        email: String,
        password: String,
        navController: NavHostController,
        mainViewModel: MainViewModel
    ) {
        when (authRepository.loginUser(email, password)) {
            is Resource.Success -> {

                mAuth.currentUser?.let {
                    val role = getUserRoleFromSharedPreferences()
                    mainViewModel.updateSelection(role)
                    saveUserCredentials(email, password, role)
                }


                navController.navigate(ScreensBottomBar.Home.route)

                db.collection("users")
                    .get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {

                            val userData = document.data
                            val role = userData["Roles"].toString()
                            val userEmail = userData["Email"].toString()

                            if (userEmail == email) {
                                check(role = role, mainViewModel = mainViewModel)
                            }
                        }
                    }
                    .addOnFailureListener {

                    }


            }

            is Resource.Error -> {
                navController.navigate(ScreensBottomBar.Favorite.route)

            }

            is Resource.Loading -> {

            }
        }
    }

    private fun saveUserCredentials(email: String, password: String, role: PagesToRoles) {
        val sharedPreferences = context.getSharedPreferences("user_credentials", Context.MODE_PRIVATE)
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

    suspend fun register(email: String, password: String, navController: NavHostController) {
        when (authRepository.loginUser(email, password)) {
            is Resource.Success -> {
                navController.navigate(ScreensBottomBar.Home.route)
            }

            is Resource.Error -> {
                navController.navigate(ScreensBottomBar.Favorite.route)
            }

            is Resource.Loading -> {

            }


        }
    }

}

fun check(role: String, mainViewModel: MainViewModel) {
    if (role == "Patient") {
        Log.d("TEST1", "To Pacjent")
        mainViewModel.updateSelection(PagesToRoles.USER_LOGGED)

    }
    if (role == "Admin") {
        Log.d("TEST1", "To Admin")
        mainViewModel.updateSelection(PagesToRoles.ADMIN_LOGGED)

    }
}
