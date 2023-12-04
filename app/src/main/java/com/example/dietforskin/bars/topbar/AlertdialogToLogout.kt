package com.example.dietforskin.bars.topbar

import android.content.Context
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.dietforskin.R
import com.example.dietforskin.data.auth.AuthRepository
import com.example.dietforskin.data.auth.AuthRepositoryImpl
import com.example.dietforskin.data.auth.PagesToRoles
import com.example.dietforskin.viewmodels.MainViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AlertdialogToLogout(mainViewModel: MainViewModel) {
    val context = LocalContext.current
    val authRepository: AuthRepository =
        AuthRepositoryImpl(firebaseAuth = FirebaseAuth.getInstance(), context = context)
    AlertDialog(onDismissRequest = {
        mainViewModel.showDialog = false
    }, title = {
        Text(text = stringResource(id = R.string.log_out).lowercase())
    }, text = {
        Text(text = stringResource(id = R.string.log_out_text))
    }, confirmButton = {
        TextButton(onClick = {
            mainViewModel.logout(mainViewModel, authRepository, context)
            mainViewModel.showDialog = false
        }) {
            Text(text = stringResource(id = R.string.yes))
        }
    }, dismissButton = {
        TextButton(onClick = {
            mainViewModel.showDialog = false
        }) {
            Text(text = stringResource(id = R.string.cancel))
        }
    })
}

