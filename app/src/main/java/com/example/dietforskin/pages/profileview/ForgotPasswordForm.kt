package com.example.dietforskin.pages.profileview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dietforskin.R
import com.example.dietforskin.pages.CommonElements
import com.example.dietforskin.ui.theme.colorTextFieldsAndButton
import com.example.dietforskin.viewmodels.AuthManager
import kotlinx.coroutines.runBlocking

@Composable
fun ForgotPasswordForm(
    email: String,
    authManager: AuthManager,
    onValueChangeEmail: (String) -> Unit,
    backToLoginText: @Composable () -> Unit,
) {
    Column(modifier = Modifier.padding(15.dp)) {
        CommonElements().CustomTextFieldEmail(email = email, onValueChangeEmail = onValueChangeEmail)

        ElevatedButton(
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 35.dp), onClick = {
                if (email.isNotEmpty()) {
                    runBlocking {
                        authManager.resetPasswordPatient(email = email)
                    }
                }
            }, shape = RoundedCornerShape(0.dp), colors = ButtonDefaults.buttonColors(
                containerColor = colorTextFieldsAndButton, contentColor = Color.Black
            ), elevation = ButtonDefaults.elevatedButtonElevation(15.dp)
        ) {
            Text(text = stringResource(id = R.string.reset_Password), letterSpacing = 1.sp)
        }
        backToLoginText.invoke()
    }
}