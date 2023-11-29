package com.example.dietforskin.pages.profileview

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.dietforskin.R
import com.example.dietforskin.data.auth.AuthRepository
import com.example.dietforskin.data.auth.PagesToRoles
import com.example.dietforskin.elements.CustomTextField
import com.example.dietforskin.pages.CommonElements
import com.example.dietforskin.ui.theme.colorTextFieldsAndButton
import com.example.dietforskin.viewmodels.AuthManager
import com.example.dietforskin.viewmodels.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun LoginForm(
    email: String,
    authRepository: AuthRepository,
    password: String,
    visualTransformation: VisualTransformation,
    coroutineScope: CoroutineScope,
    authManager: AuthManager,
    navController: NavHostController,
    mainViewModel: MainViewModel,
    onValueChangeEmail: (String) -> Unit,
    onValueChangePassword: (String) -> Unit,
    trailingIcon: @Composable () -> Unit,
    forgetText: @Composable () -> Unit,
    context: Context,
    notLogged: Boolean,
) {
    Column(modifier = Modifier.padding(15.dp)) {
        CommonElements().CustomTextFieldEmail(email = email, onValueChangeEmail = onValueChangeEmail)

        Spacer(modifier = Modifier.padding(12.dp))

        CustomTextFieldPassword(
            password = password,
            onValueChangePassword = onValueChangePassword,
            visualTransformation = visualTransformation,
            trailingIcon = trailingIcon
        )

        ElevatedButton(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(top = 20.dp), onClick = {
                coroutineScope.launch {
                    authManager.login(email, password, navController, mainViewModel)
                }
            }, shape = RoundedCornerShape(0.dp), colors = ButtonDefaults.buttonColors(
                containerColor = colorTextFieldsAndButton, contentColor = Color.Black
            ), elevation = ButtonDefaults.elevatedButtonElevation(15.dp)
        ) {
            Text(text = "LOGIN IN", letterSpacing = 1.sp)
        }

        ElevatedButton(
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 35.dp), onClick = {
                authRepository.logoutUser()
                mainViewModel.updateSelection(PagesToRoles.NOT_LOGGED)
                val sharedPreferences =
                    context.getSharedPreferences("user_credentials", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.clear()
                editor.apply()
            }, shape = RoundedCornerShape(0.dp), colors = ButtonDefaults.buttonColors(
                containerColor = colorTextFieldsAndButton, contentColor = Color.Black
            ), enabled = !notLogged, elevation = ButtonDefaults.elevatedButtonElevation(15.dp)
        ) {
            Text(text = "SIGN OUT", letterSpacing = 1.sp)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.End)
        ) {
            forgetText.invoke()
        }
    }
}

@Composable
fun CustomTextFieldPassword(
    password: String,
    onValueChangePassword: (String) -> Unit,
    visualTransformation: VisualTransformation,
    trailingIcon: @Composable () -> Unit
) {
    CustomTextField(
        value = password,
        onValueChange = onValueChangePassword,
        label = {
            Text(text = "PASSWORD", letterSpacing = 1.sp)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon
    )
}