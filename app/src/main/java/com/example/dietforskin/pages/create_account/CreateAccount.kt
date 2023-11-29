package com.example.dietforskin.pages.create_account

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.dietforskin.data.auth.AuthRepository
import com.example.dietforskin.data.auth.AuthRepositoryImpl
import com.example.dietforskin.data.profile.person.Person
import com.example.dietforskin.elements.CustomTextField
import com.example.dietforskin.pages.CommonElements
import com.example.dietforskin.report.Reports
import com.example.dietforskin.ui.theme.colorCircle
import com.example.dietforskin.ui.theme.colorTextFieldsAndButton
import com.example.dietforskin.viewmodels.AuthManager
import com.example.dietforskin.viewmodels.MainViewModel
import com.example.dietforskin.viewmodels.PagesViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAccount(navController: NavHostController) {
    val db = Firebase.firestore

    val pagesViewModel = remember { PagesViewModel() }
    val username by pagesViewModel.username.collectAsState()
    val email by pagesViewModel.email.collectAsState()
    val password by pagesViewModel.password.collectAsState()
    val role by pagesViewModel.selectedRole.collectAsState()

    var isFold by remember {
        mutableStateOf(false)
    }

    val authRepository: AuthRepository = AuthRepositoryImpl()
    val context = LocalContext.current
    val authManager = AuthManager(authRepository, context)
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding()
    ) {
        Spacer(modifier = Modifier.border(1.dp, Color.Black))
        Canvas(modifier = Modifier.align(alignment = Alignment.TopEnd), onDraw = {
            drawCircle(
                color = colorCircle, radius = 450.dp.toPx()
            )
        })

        CommonElements().canvasWithName("CREATE\nACCOUNT")

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(start = 40.dp, end = 40.dp)
        ) {
            Column(modifier = Modifier.padding(15.dp)) {

                CustomTextFieldUsername(
                    username = username,
                    onValueChangeUsername = pagesViewModel::onUsernameChanged
                )

                Spacer(modifier = Modifier.padding(12.dp))

                CommonElements().CustomTextFieldEmail(
                    email = email,
                    onValueChangeEmail = pagesViewModel::onEmailChanged
                )

                Spacer(modifier = Modifier.padding(12.dp))

                Box(modifier = Modifier.fillMaxWidth()) {
                    ElevatedButton(
                        modifier = Modifier
                            .padding(
                                top = 20.dp
                            )
                            .align(Alignment.CenterStart), onClick = {
                            isFold = !isFold
                        }, shape = RoundedCornerShape(0.dp), colors = ButtonDefaults.buttonColors(
                            containerColor = colorTextFieldsAndButton, contentColor = Color.Black
                        ), elevation = ButtonDefaults.elevatedButtonElevation(15.dp)
                    ) {
                        Text(text = role, letterSpacing = 1.sp)
                    }

                    if (isFold) {
                        ShowDropMenu(
                            isFold = isFold,
                            onRoleSelected = {
                                pagesViewModel.onSelectedRoleChanged(it); isFold = false
                            })
                    }

                    ElevatedButton(
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .align(Alignment.CenterEnd),
                        onClick = {

                            pagesViewModel.onPasswordChanged(generateRandom())

                            val uuid = UUID.randomUUID().toString()
                            if (username.isNotEmpty() &&
                                password.isNotEmpty() &&
                                email.isNotEmpty() &&
                                role != "ROLE"
                            ) {
                                coroutineScope.launch {
                                    authManager.register(
                                        email = email,
                                        password = password,
                                        navController = navController
                                    )
                                }
                                db.collection("users").add(
                                    Person(
                                        username = username,
                                        email = email,
                                        role = role,
                                        uuid = uuid
                                    )
                                ).addOnSuccessListener {
                                    Reports(context = context).savedInDatabase()
                                }.addOnFailureListener {
                                    Reports(context = context).errorForSavingToDatabase()
                                }
                            } else {
                                Reports(context = context).errorFillAllFields()
                            }
                        },
                        shape = RoundedCornerShape(0.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorTextFieldsAndButton, contentColor = Color.Black
                        ),
                        elevation = ButtonDefaults.elevatedButtonElevation(15.dp)
                    ) {
                        Text(text = "CREATE", letterSpacing = 1.sp)
                    }
                }

            }
        }
    }
}

@Composable
fun CustomTextFieldUsername(username: String, onValueChangeUsername: (String) -> Unit) {
    CustomTextField(
        value = username,
        onValueChange = onValueChangeUsername,
        label = {
            Text(text = ("USERNAME"), letterSpacing = 1.sp)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    )
}


@Composable
fun ShowDropMenu(isFold: Boolean, onRoleSelected: (String) -> Unit) {
    DropdownMenu(
        expanded = isFold,
        onDismissRequest = { !isFold },
        modifier = Modifier
            .width(100.dp)
            .height(100.dp)
    ) {
        DropdownMenuItem(text = { Text("Admin") }, onClick = {
            onRoleSelected("Admin")
        })
        DropdownMenuItem(text = { Text("Patient") }, onClick = {
            onRoleSelected("Patient")
        })
    }
}

fun generateRandom(): String {
    val usedIds = mutableSetOf<String>()
    var letterUUID: String
    do {
        val standardUUID = UUID.randomUUID()

        val hexString = standardUUID.toString().replace("-", "").substring(0, 15)

        letterUUID = hexString
    } while (!usedIds.add(letterUUID))

    return letterUUID
}



