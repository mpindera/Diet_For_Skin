package com.example.dietforskin.pages.chat_view

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.dietforskin.data.profile.person.Patient
import com.example.dietforskin.viewmodels.MainViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

@Composable
fun Chat(navController: NavHostController, context: Context, mainViewModel: MainViewModel) {
    val db = Firebase.firestore

    var isPat by remember {
        mutableStateOf(false)
    }
    val patients = remember { mutableStateListOf<Patient>() }

    LaunchedEffect(Unit) {
        val documents = db.collection("users").get().await()

        for (document in documents) {
            val userData = document.data
            val role = userData["role"].toString()
            val userEmail = userData["email"].toString()
            val username = userData["username"].toString()
            val uuid = userData["uuid"].toString()
            val currentUserDocRef = db.collection("users").document(document.id)

//            currentUserDocRef.update("listOfPatients", uuid)

            if (role != "Admin") {
                patients.add(Patient(username = username, email = userEmail, uuid = uuid))

                isPat = true
            }
        }
    }

    if (isPat) {
        LazyColumn {
            items(patients) {
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(15.dp)
                ) {
                    IconButton(onClick = {

                    }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    }

                    /*
                        Text(
                            text = it.username, modifier = Modifier
                                .align(CenterHorizontally)
                                .padding(5.dp)
                        )
                        Text(
                            text = it.email, modifier = Modifier
                                .align(CenterHorizontally)
                                .padding(5.dp)
                        )*/

                }
            }
        }
    }
}

