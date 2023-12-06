package com.example.dietforskin.pages.profile_view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.dietforskin.R
import com.example.dietforskin.elements.CustomTextField
import com.example.dietforskin.pages.CommonElements
import com.example.dietforskin.ui.theme.colorTextFieldsAndButton
import com.example.dietforskin.viewmodels.AuthManager
import com.example.dietforskin.viewmodels.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginForm(
    email: String,
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
) {

    Column(modifier = Modifier.padding(15.dp)) {
        CommonElements().CustomTextFieldEmail(
            email = email.lowercase(),
            onValueChangeEmail = onValueChangeEmail
        )

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
                .padding(top = 20.dp),
            enabled = authManager.checkingAllFields(email, password),
            onClick = {
                coroutineScope.launch {
                    authManager.login(email, password, navController, mainViewModel)
                }
            }, shape = RoundedCornerShape(0.dp), colors = ButtonDefaults.buttonColors(
                containerColor = colorTextFieldsAndButton, contentColor = Color.Black
            ), elevation = ButtonDefaults.elevatedButtonElevation(15.dp)
        ) {
            Text(text = stringResource(id = R.string.login_in), letterSpacing = 1.sp)

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
            Text(text = stringResource(id = R.string.password), letterSpacing = 1.sp)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon
    )
}