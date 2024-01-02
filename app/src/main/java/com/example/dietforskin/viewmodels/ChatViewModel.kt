package com.example.dietforskin.viewmodels

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.dietforskin.data.profile.person.Admin
import com.example.dietforskin.data.profile.person.Patient
import com.example.dietforskin.pages.CommonElements
import com.example.dietforskin.pages.chat_view.ChatAdmin
import com.example.dietforskin.pages.chat_view.ChatPatient
import com.example.dietforskin.ui.theme.colorOfBorder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await

class ChatViewModel : ViewModel() {

  private val _patients = mutableStateListOf<Patient>()
  private val patients: List<Patient> get() = _patients


  private val _patientsAll = mutableStateListOf<Patient>()
  val patientsAll: List<Patient> get() = _patientsAll


  var dietitian by mutableStateOf("")
    private set

  fun addDietitian(dietitian: String) {
    this.dietitian = dietitian
  }


  private fun addPatient(patient: Patient) {
    if (!_patients.contains(patient)) {
      _patients.add(patient)
    }
  }

  private fun addAllPatient(patient: Patient) {
    if (!_patientsAll.contains(patient)) {
      _patientsAll.add(patient)
    }
  }

  var showListOfPatients by mutableStateOf(false)
    private set

  fun onListOfPatientsChanged(showListOfPatients: Boolean) {
    this.showListOfPatients = showListOfPatients
  }

  //---//

  private var _personRole = MutableStateFlow("")
  val personRole: StateFlow<String> = _personRole

  private fun onPersonRoleChanged(personRole: String) {
    _personRole.value = personRole
  }

  private var _personUUID = MutableStateFlow("")
  val personUUID: StateFlow<String> = _personUUID

  private fun onPersonUUIDChanged(personUUID: String) {
    _personUUID.value = personUUID
  }

  @Composable
  fun GetPatientsFromDatabaseToDirectDietitian() {

    LaunchedEffect(Unit) {
      val mAuthCurrentAdminEmail = CommonElements().mAuth?.email

      val arrayOfList: ArrayList<String> = arrayListOf()
      try {
        for (document in CommonElements().dbGet.await()) {
          val userData = document.data
          val role = userData["role"].toString()
          val userEmail = userData["email"].toString()
          val name = userData["name"].toString()
          val surname = userData["surname"].toString()
          val uuid = userData["uuid"].toString()
          val patientList = userData["listOfPatients"] as? ArrayList<*>

          addPatient(
            Patient(
              name = name, surname = surname, email = userEmail, uuid = uuid
            )
          )
          if (userEmail == mAuthCurrentAdminEmail && role == CommonElements().admin) {
            if (patientList != null) {
              for (i in patientList) {
                arrayOfList.add(i.toString())
              }
            }
          }
          for (i in arrayOfList) {
            for (j in patients) {
              if (i.contains(j.uuid)) {
                addAllPatient(Patient(j.name, j.surname, j.email, j.uuid))
              }
            }
          }
        }
        onListOfPatientsChanged(true)
      } catch (e: Exception) {
        Log.e("Error Fetching", "Error fetching user data", e)
      }
    }
  }

  @Composable
  fun checkIfAdminOrPatient(navController: NavHostController) {
    var isOperationComplete by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
      val mAuthCurrentAdminEmail = CommonElements().mAuth?.email

      try {
        for (document in CommonElements().dbGet.await()) {
          val userData = document.data
          val userEmail = userData["email"].toString()
          val role = userData["role"].toString()
          val uuid = userData["uuid"].toString()


          if (userEmail == mAuthCurrentAdminEmail && role == CommonElements().admin) {
            onPersonRoleChanged(CommonElements().admin)
          }

          if (userEmail == mAuthCurrentAdminEmail && role == CommonElements().patient) {
            onPersonRoleChanged(CommonElements().patient)
            onPersonUUIDChanged(uuid)
          }
        }
      } catch (e: Exception) {
        Log.e("Error Fetching", "Error fetching user data", e)
      } finally {
        isOperationComplete = true
      }
    }
    if (isOperationComplete) {
      when (personRole.value) {
        CommonElements().admin -> {
          ChatAdmin(this, navController)
        }

        CommonElements().patient -> {
          ChatPatient(this, navController)
        }
      }
    }
  }

  @Composable
  fun GetDietitianFromDatabaseToDirectPatient() {

    LaunchedEffect(Unit) {
      val mAuthCurrentAdminEmail = CommonElements().mAuth?.email

      try {
        for (document in CommonElements().dbGet.await()) {
          val userData = document.data
          val role = userData["role"].toString()
          val userEmail = userData["email"].toString()
          val dietitian = userData["dietitian"].toString()

          if (userEmail == mAuthCurrentAdminEmail && role == CommonElements().patient) {
            addDietitian(dietitian)
          }

        }
      } catch (e: Exception) {
        Log.e("Error Fetching", "Error fetching user data", e)
      }
    }
  }

  @Composable
  fun DividerInChat() {
    Box(
      modifier = Modifier
        .fillMaxHeight()
        .width(1.dp)
        .background(colorOfBorder)
        .padding(5.dp)
    )
  }
}
