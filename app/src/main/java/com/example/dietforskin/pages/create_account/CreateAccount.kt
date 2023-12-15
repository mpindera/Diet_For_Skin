package com.example.dietforskin.pages.create_account

import android.content.Context
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.dietforskin.R
import com.example.dietforskin.data.auth.AuthRepository
import com.example.dietforskin.data.auth.AuthRepositoryImpl
import com.example.dietforskin.data.database.DatabaseRepositoryImpl
import com.example.dietforskin.data.profile.person.Person
import com.example.dietforskin.elements.CustomTextField
import com.example.dietforskin.pages.CommonElements
import com.example.dietforskin.ui.theme.colorCircle
import com.example.dietforskin.ui.theme.colorTextFieldsAndButton
import com.example.dietforskin.viewmodels.AuthManager
import com.example.dietforskin.viewmodels.PagesViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID

/** CreateAccount is a class where admin can add profile and role to them.
Admin has to write username, email, role, but password is generated automatically.
 **/
@Composable
fun CreateAccount(navController: NavHostController, context: Context) {

    val pagesViewModel = remember { PagesViewModel() }
    val username by pagesViewModel.username.collectAsState()
    val email by pagesViewModel.email.collectAsState()
    val role by pagesViewModel.selectedRole.collectAsState()
    val dietitian by pagesViewModel.selectedDietitian.collectAsState()
    val checkIfPatient = pagesViewModel.selectedRole.value == "Patient"
    val checkIfAdmin = pagesViewModel.selectedRole.value == "Admin"

    var isFold by remember {
        mutableStateOf(false)
    }

    var isFoldAdmin by remember {
        mutableStateOf(false)
    }

    val authRepository: AuthRepository =
        AuthRepositoryImpl(firebaseAuth = FirebaseAuth.getInstance(), context = context)
    val authManager = AuthManager(authRepository, context)
    val coroutineScope = rememberCoroutineScope()

    val focusManager = LocalFocusManager.current
    val typingJob by remember { mutableStateOf<Job?>(null) }
    val generatedPassword = generateRandom()
    pagesViewModel.onPasswordChanged(generatedPassword)
    val uuid = UUID.randomUUID().toString()

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

        CommonElements().canvasWithName(stringResource(id = R.string.create_account))

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(start = 40.dp, end = 40.dp)
        ) {
            Column(modifier = Modifier.padding(15.dp)) {

                if (checkIfAdmin) {
                    CreateAccountAdmin(pagesViewModel)
                } else if (checkIfPatient) {
                    CreateAccountPatient(pagesViewModel)
                }
                
                Box(modifier = Modifier.fillMaxWidth()) {
                    if (checkIfPatient) {
                        CreateAccountRole(pagesViewModel = pagesViewModel)
                        CreateAccountPatientChooseDietitian(pagesViewModel, dietitian)
                    }
                }

                Box(modifier = Modifier.fillMaxWidth()) {
                    ElevatedButton(
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .align(BottomEnd),
                        enabled = authManager.checkingAllFields(username, email, role, uuid),
                        onClick = {
                            coroutineScope.launch {
                                addToDietitian(uuid)
                                addCreatedAccountToDatabase(
                                    context = context,
                                    navController = navController,
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
                        },
                        shape = RoundedCornerShape(0.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorTextFieldsAndButton, contentColor = Color.Black
                        ),
                        elevation = ButtonDefaults.elevatedButtonElevation(15.dp)
                    ) {
                        Text(text = stringResource(id = R.string.create), letterSpacing = 1.sp)
                    }
                    if (checkIfAdmin || role == "ROLE") {
                        CreateAccountRole(pagesViewModel = pagesViewModel)
                    }
                }
            }
        }
    }
}

suspend fun addToDietitian(uuid: String) {
    val db = Firebase.firestore
    val mAuthCurrentAdminEmail = FirebaseAuth.getInstance().currentUser?.email

    val documents = db.collection("users").get().await()

    for (document in documents) {
        val userData = document.data
        val userEmail = userData["email"].toString()
        val userAdmin = userData["role"].toString()
        val currentUserDocRef = db.collection("users").document(document.id)

        if (userEmail == mAuthCurrentAdminEmail && userAdmin == "Admin") {
            currentUserDocRef.update("listOfPatients", FieldValue.arrayUnion(uuid))

        }
    }
}

suspend fun addCreatedAccountToDatabase(
    context: Context,
    navController: NavHostController,
    authManager: AuthManager,
    pagesViewModel: PagesViewModel,
    username: String,
    email: String,
    role: String,
    uuid: String,
    dietitian: String,
    generatedPassword: String,
    focusManager: FocusManager,
    typingJob: Job?
) {
    DatabaseRepositoryImpl(
        database = Firebase, context = context
    ).addPersonToDatabase(
        Person(
            username = username, email = email, role = role, uuid = uuid, dietitian = dietitian
        )
    )
    authManager.register(
        email = email, password = generatedPassword, navController = navController
    )
    pagesViewModel.clearFields()
    typingJob?.cancel()
    focusManager.clearFocus()
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

@Composable
fun ShowDropMenuForDietitian(
    isFoldAdmin: Boolean,
    onRoleSelected: (String) -> Unit,
) {
    val adminUsernames = remember { mutableStateListOf<String>() }
    val coroutineScope = rememberCoroutineScope()
    DropdownMenu(
        expanded = isFoldAdmin,
        onDismissRequest = { !isFoldAdmin },
        modifier = Modifier
            .width(100.dp)
            .height(100.dp)
    ) {
        coroutineScope.launch {
            val currentUserEmail = Firebase.auth.currentUser?.email
            val documents = Firebase.firestore.collection("users").get().await()
            adminUsernames.clear()
            for (document in documents) {
                val userData = document.data
                val role = userData["role"].toString()
                val username = userData["username"].toString()
                val email = userData["email"].toString()

                if (currentUserEmail == email && role == "Admin") {
                    adminUsernames.add(username)
                }
            }
        }
        for (username in adminUsernames) {
            DropdownMenuItem(text = {
                Text(username)
            }, onClick = {
                onRoleSelected(username)
            })

        }
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



