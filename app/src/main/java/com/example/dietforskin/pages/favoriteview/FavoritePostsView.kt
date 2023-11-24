package com.example.dietforskin.pages.favoriteview

import android.content.Context
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.dietforskin.data.auth.AuthRepository
import com.example.dietforskin.data.auth.AuthRepositoryImpl
import com.example.dietforskin.data.auth.PagesToRoles
import com.example.dietforskin.viewmodels.MainViewModel

@Composable
fun FavoritePostsView(mainViewModel: MainViewModel,context: Context) {
    Text(text = "Fav")
    val authRepository: AuthRepository = AuthRepositoryImpl()

    Button(
        onClick = {
            authRepository.logoutUser()
            mainViewModel.updateSelection(PagesToRoles.NOT_LOGGED)
            val sharedPreferences =
                context.getSharedPreferences("user_credentials", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
        }
    ) {
        Text("Wyloguj się")
    }
}