package com.example.dietforskin.bars.topbar

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.dietforskin.R
import com.example.dietforskin.data.auth.AuthRepository
import com.example.dietforskin.data.auth.AuthRepositoryImpl
import com.example.dietforskin.viewmodels.MainViewModel
import com.example.dietforskin.viewmodels.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AlertdialogToLogout(profileViewModel: ProfileViewModel, navController: NavHostController) {
    val context = LocalContext.current
    val authRepository: AuthRepository =
        AuthRepositoryImpl(firebaseAuth = FirebaseAuth.getInstance(), context = context)
    AlertDialog(onDismissRequest = {
        profileViewModel.showDialog = false
    }, title = {
        Text(text = stringResource(id = R.string.log_out).lowercase())
    }, text = {
        Text(text = stringResource(id = R.string.log_out_text))
    }, confirmButton = {
        TextButton(onClick = {
            profileViewModel.logout(
                profileViewModel = profileViewModel,
                authRepository = authRepository,
                context = context,
                navController = navController
            )
            profileViewModel.showDialog = false
        }) {
            Text(text = stringResource(id = R.string.yes))
        }
    }, dismissButton = {
        TextButton(onClick = {
            profileViewModel.showDialog = false
        }) {
            Text(text = stringResource(id = R.string.cancel))
        }
    })
}

