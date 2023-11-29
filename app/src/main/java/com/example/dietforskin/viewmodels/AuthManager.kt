package com.example.dietforskin.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import com.example.dietforskin.bars.bottombar.ScreensBottomBar
import com.example.dietforskin.data.auth.AuthRepository
import com.example.dietforskin.data.auth.PagesToRoles
import com.example.dietforskin.data.auth.Resource
import com.example.dietforskin.report.Reports
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await


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
                            val role = userData["role"].toString()
                            val userEmail = userData["email"].toString()
                            val username = userData["username"].toString()

                            if (userEmail == email) {
                                check(
                                    role = role,
                                    username = username,
                                    mainViewModel = mainViewModel,
                                    context = context
                                )
                            }
                        }
                        mainViewModel.updateSelectedScreen(ScreensBottomBar.Home)
                    }
                    .addOnFailureListener {
                        Reports(context = context).errorFetchFromDatabase()
                    }
            }

            is Resource.Error -> {
                navController.navigate(ScreensBottomBar.Profile.route)
            }

            is Resource.Loading -> {

            }
        }
    }

    suspend fun resetPasswordPatient(
        email: String
    ) = coroutineScope {
        try {
            val documents = db.collection("users").get().await()
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

    suspend fun register(email: String, password: String, navController: NavHostController) {
        when (authRepository.registerUser(email, password)) {
            is Resource.Success -> {
                Reports(context).u()
            }

            is Resource.Error -> {
                Reports(context).s()
            }

            is Resource.Loading -> {

            }


        }
    }

}

fun check(role: String, mainViewModel: MainViewModel, context: Context, username: String) {
    if (role == "Patient") {
        Toast.makeText(context, "Hello, $username.", Toast.LENGTH_SHORT).show()
        mainViewModel.updateSelection(PagesToRoles.PATIENT_LOGGED)
    }
    if (role == "Admin") {
        Toast.makeText(context, "Hello, $username.", Toast.LENGTH_SHORT).show()
        mainViewModel.updateSelection(PagesToRoles.ADMIN_LOGGED)
    }
}
