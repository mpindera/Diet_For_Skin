package com.example.dietforskin.pages.create_account

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.dietforskin.R
import com.example.dietforskin.ui.theme.colorTextFieldsAndButton
import com.example.dietforskin.viewmodels.AuthManager
import com.example.dietforskin.viewmodels.PagesViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun CreateAccountButton(
    authManager: AuthManager,
    username: String,
    email: String,
    role: String,
    uuid: String,
    context: Context,
    navController: NavHostController,
    pagesViewModel: PagesViewModel,
    dietitian: String,
    generatedPassword: String,
    focusManager: FocusManager,
    typingJob: Job?,
    checkIfAdmin: Boolean,
    passwordAdmin: String
) {
    var isFold by remember {
        mutableStateOf(false)
    }

    var isFoldAdmin by remember {
        mutableStateOf(false)
    }
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxWidth()) {
        ElevatedButton(
            modifier = Modifier
                .padding(top = 20.dp)
                .align(Alignment.BottomEnd),
            enabled = authManager.checkingAllFields(username, email, role, uuid),
            onClick = {
                if (checkIfAdmin) {
                    coroutineScope.launch {
                        addCreatedAdminToDatabase(
                            context = context,
                            authManager = authManager,
                            pagesViewModel = pagesViewModel,
                            username = username,
                            email = email,
                            role = role,
                            uuid = uuid,
                            password = passwordAdmin,
                            focusManager = focusManager,
                            typingJob = typingJob
                        )
                        isFold = false
                        isFoldAdmin = false
                    }
                } else {
                    coroutineScope.launch {
                        addToDietitian(uuid)
                        addCreatedAccountToDatabase(
                            context = context,
                            authManager = authManager,
                            pagesViewModel = pagesViewModel,
                            username = username,
                            email = email,
                            role = role,
                            uuid = uuid,
                            dietitian = dietitian,
                            generatedPassword = generatedPassword,
                            focusManager = focusManager,
                            typingJob = typingJob
                        )
                        isFold = false
                        isFoldAdmin = false
                    }
                }
            },
            shape = RoundedCornerShape(0.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorTextFieldsAndButton, contentColor = Color.Black
            ),
            elevation = ButtonDefaults.elevatedButtonElevation(15.dp)
        ) {
            Text(text = stringResource(id = R.string.create), letterSpacing = 1.sp)
        }
    }
}