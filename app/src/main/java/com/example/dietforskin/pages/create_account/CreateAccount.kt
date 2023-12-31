package com.example.dietforskin.pages.create_account

import android.content.Context
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
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
import com.example.dietforskin.data.profile.PagesSite
import com.example.dietforskin.data.profile.person.Admin
import com.example.dietforskin.data.profile.person.Person
import com.example.dietforskin.elements.CustomTextField
import com.example.dietforskin.pages.CommonElements
import com.example.dietforskin.report.Reports
import com.example.dietforskin.viewmodels.AuthManager
import com.example.dietforskin.viewmodels.PagesViewModel
import com.example.dietforskin.viewmodels.ProfileViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
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
fun CreateAccount(
  navController: NavHostController, context: Context, profileViewModel: ProfileViewModel
) {

  val pagesViewModel = remember { PagesViewModel() }
  val name by pagesViewModel.name.collectAsState()
  val surname by pagesViewModel.surname.collectAsState()
  val email by pagesViewModel.email.collectAsState()
  val role by pagesViewModel.selectedRole.collectAsState()
  val dietitian by pagesViewModel.selectedDietitian.collectAsState()
  val checkIfPatient = pagesViewModel.selectedRole.value == CommonElements().patient
  val checkIfAdmin = pagesViewModel.selectedRole.value == CommonElements().admin
  val visibility by pagesViewModel.visibilityOfAnimation.collectAsState()

  val authRepository: AuthRepository =
    AuthRepositoryImpl(firebaseAuth = FirebaseAuth.getInstance(), context = context)
  val authManager = AuthManager(authRepository, context)


  val focusManager = LocalFocusManager.current
  val typingJob by remember { mutableStateOf<Job?>(null) }

  val generatedPassword = generateRandom()
  pagesViewModel.onPasswordChanged(generatedPassword)

  val uuid = UUID.randomUUID().toString()

  if (checkIfAdmin || checkIfPatient) {
    pagesViewModel.onVisibilityChanged(true)
  }
  profileViewModel.updateSelectionOfPagesSite(PagesSite.CREATE_ACCOUNT_VIEW)
  Box(
    modifier = Modifier
      .fillMaxSize()
      .padding()

  ) {
    CommonElements().CanvasBackground(modifier = Modifier.align(alignment = Alignment.TopEnd))

    CommonElements().canvasWithName(stringResource(id = R.string.create_account))

    Box(
      modifier = Modifier
        .align(Alignment.Center)
        .padding(start = 40.dp, end = 40.dp)
    ) {
      Column(modifier = Modifier.padding(15.dp)) {

        AnimatedVisibility(
          visible = visibility,
          enter = fadeIn(
            animationSpec = tween(
              durationMillis = 1000, easing = FastOutSlowInEasing
            )
          ),
          exit = fadeOut(animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing))
        ) {
          Crossfade(
            targetState = if (checkIfAdmin) CommonElements().admin else CommonElements().patient,
            label = "animationAccount",
            animationSpec = tween(durationMillis = 500, easing = FastOutLinearInEasing)
          ) { target ->
            when (target) {
              CommonElements().admin -> CreateAccountAdmin(pagesViewModel)
              CommonElements().patient -> CreateAccountPatient(pagesViewModel)
            }
          }
        }

        Box(modifier = Modifier.fillMaxWidth()) {
          CreateAccountRole(pagesViewModel = pagesViewModel)
          if (checkIfPatient) {
            CreateAccountPatientChooseDietitian(pagesViewModel, dietitian)
          }
        }

        CreateAccountButton(
          authManager = authManager,
          name = name,
          surname = surname,
          email = email,
          role = role,
          uuid = uuid,
          context = context,
          navController = navController,
          pagesViewModel = pagesViewModel,
          dietitian = dietitian,
          generatedPassword = generatedPassword,
          focusManager = focusManager,
          typingJob = typingJob,
          checkIfAdmin = checkIfAdmin
        )

      }
    }
  }
}

suspend fun addToDietitian(uuid: String) {
  val mAuthCurrentAdminEmail = FirebaseAuth.getInstance().currentUser?.email

  try {
    for (document in CommonElements().dbGet.await()) {
      val userData = document.data
      val userEmail = userData["email"].toString()
      val userAdmin = userData["role"].toString()
      val currentUserDocRef = CommonElements().dbDocument.document(document.id)

      if (userEmail == mAuthCurrentAdminEmail && userAdmin == CommonElements().admin) {
        currentUserDocRef.update("listOfPatients", FieldValue.arrayUnion(uuid))

      }
    }
  } catch (e: Exception) {
    Log.e("Error Fetching", "Error fetching user data", e)
  }
}

suspend fun addCreatedAccountToDatabase(
  context: Context,
  authManager: AuthManager,
  pagesViewModel: PagesViewModel,
  name: String,
  surname: String,
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
      name = name, surname = surname, email = email, role = role, uuid = uuid, dietitian = dietitian
    )
  )
  authManager.register(
    email = email, password = generatedPassword
  )
  pagesViewModel.clearFields()
  typingJob?.cancel()
  focusManager.clearFocus()
}

suspend fun addCreatedAdminToDatabase(
  context: Context,
  authManager: AuthManager,
  pagesViewModel: PagesViewModel,
  name: String,
  surname: String,
  email: String,
  role: String,
  uuid: String,
  password: String,
  focusManager: FocusManager,
  typingJob: Job?
) {
  try {
    DatabaseRepositoryImpl(
      database = Firebase, context = context
    ).addAdminToDatabase(
      Admin(
        name = name,
        surname = surname,
        email = email,
        role = role,
        uuid = uuid,
        listOfPatients = emptyList()
      )
    )
    authManager.register(
      email = email, password = password
    )
  } catch (e: Exception) {
    println(e.toString())
  }
  pagesViewModel.clearFields()
  typingJob?.cancel()
  focusManager.clearFocus()
}


@Composable
fun CustomTextFieldName(name: String, onValueChangeName: (String) -> Unit) {
  CustomTextField(
    value = name,
    onValueChange = onValueChangeName,
    label = {
      Text(text = ("NAME"), letterSpacing = 1.sp)
    },
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
  )
}

@Composable
fun CustomTextFieldSurname(surname: String, onValueChangeSurname: (String) -> Unit) {
  CustomTextField(
    value = surname,
    onValueChange = onValueChangeSurname,
    label = {
      Text(text = ("SURNAME"), letterSpacing = 1.sp)
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
    DropdownMenuItem(text = { Text(CommonElements().admin) }, onClick = {
      onRoleSelected(CommonElements().admin)
    })
    DropdownMenuItem(text = { Text(CommonElements().patient) }, onClick = {
      onRoleSelected(CommonElements().patient)

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
      try {
        val documents = Firebase.firestore.collection("users").get().await()

        adminUsernames.clear()

        for (document in documents) {
          val userData = document.data
          val role = userData["role"].toString()
          val name = userData["name"].toString()

          if (role == CommonElements().admin) {
            adminUsernames.add(name)
          }
        }
      } catch (e: Exception) {
        Log.e("ShowDropMenuForDietitian", "Error fetching user data", e)
      }
    }

    for (username in adminUsernames) {
      DropdownMenuItem(text = { Text(username) }, onClick = {
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



