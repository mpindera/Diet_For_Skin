package com.example.dietforskin.viewmodels

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.dietforskin.data.profile.person.Patient
import com.example.dietforskin.pages.CommonElements
import kotlinx.coroutines.tasks.await

class ChatViewModel : ViewModel() {

    private val _patients = mutableStateListOf<Patient>()
    val patients: List<Patient> get() = _patients


    private val _patientsAll = mutableStateListOf<Patient>()
    val patientsAll: List<Patient> get() = _patientsAll

    private fun addPatient(patient: Patient) {
        _patients.add(patient)
    }
    private fun addAllPatient(patient: Patient) {
        _patientsAll.add(patient)
    }

    var showListOfPatients by mutableStateOf(false)
        private set

    fun onListOfPatientsChanged(showListOfPatients: Boolean) {
        this.showListOfPatients = showListOfPatients
    }


    @Composable
    fun GetPatientsFromDatabaseToDirectDietitian() {

        LaunchedEffect(Unit) {
            val documents = CommonElements().db.collection("users").get().await()
            val mAuthCurrentAdminEmail = CommonElements().mAuth.currentUser?.email

            val arrayOfList: ArrayList<String> = arrayListOf()

            for (document in documents) {
                val userData = document.data
                val role = userData["role"].toString()
                val userEmail = userData["email"].toString()
                val username = userData["username"].toString()
                val uuid = userData["uuid"].toString()
                val patientList = userData["listOfPatients"] as? ArrayList<*>

                addPatient(Patient(username = username, email = userEmail, uuid = uuid))

                if (userEmail == mAuthCurrentAdminEmail && role == "Admin") {
                    if (patientList != null) {
                        for (i in patientList) {
                            arrayOfList.add(i.toString())
                        }
                    }
                }
                for (i in arrayOfList) {
                    for (j in patients) {
                        if (i.contains(j.uuid)) {
                            addAllPatient(Patient(j.username, j.email, j.uuid))
                        }
                    }
                }
            }
            onListOfPatientsChanged(true)
        }

    }
}