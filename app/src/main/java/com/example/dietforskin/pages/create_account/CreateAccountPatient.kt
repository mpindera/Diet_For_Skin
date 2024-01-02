package com.example.dietforskin.pages.create_account

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dietforskin.pages.CommonElements
import com.example.dietforskin.ui.theme.colorTextFieldsAndButton
import com.example.dietforskin.viewmodels.PagesViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun CreateAccountPatient(pagesViewModel: PagesViewModel) {
  val name by pagesViewModel.name.collectAsState()
  val surname by pagesViewModel.surname.collectAsState()
  val email by pagesViewModel.email.collectAsState()

  Column {
    CustomTextFieldName(
      name = name, onValueChangeName = pagesViewModel::onNameChanged
    )

    Spacer(modifier = Modifier.padding(12.dp))

    CustomTextFieldSurname(
      surname = surname, onValueChangeSurname = pagesViewModel::onSurnameChanged
    )

    Spacer(modifier = Modifier.padding(12.dp))

    CommonElements().CustomTextFieldEmail(
      email = email, onValueChangeEmail = pagesViewModel::onEmailChanged
    )
  }

}

@Composable
fun CreateAccountPatientChooseDietitian(pagesViewModel: PagesViewModel, dietitian: String) {
  val coroutineScope = rememberCoroutineScope()
  val currentUser = CommonElements().mAuth?.email

  Box(modifier = Modifier.fillMaxWidth()) {
    ElevatedButton(
      modifier = Modifier
        .padding(top = 20.dp)
        .align(Alignment.BottomEnd), onClick = {

      }, shape = RoundedCornerShape(0.dp), colors = ButtonDefaults.buttonColors(
        containerColor = colorTextFieldsAndButton, contentColor = Color.Black
      ), elevation = ButtonDefaults.elevatedButtonElevation(15.dp)
    ) {
      Text(text = dietitian.replace(" ", "\n"), letterSpacing = 1.sp, fontSize = 13.sp, textAlign = TextAlign.Center)
    }
    Column(
      modifier = Modifier
        .padding(
          top = 20.dp
        )
        .align(Alignment.BottomEnd)
    ) {
      coroutineScope.launch {
        try {
          val documents = Firebase.firestore.collection("users").get().await()

          for (document in documents) {
            val userData = document.data
            val role = userData["role"].toString()
            val name = userData["name"].toString()
            val surname = userData["surname"].toString()
            val email = userData["email"].toString()

            if (role == CommonElements().admin && currentUser == email) {
              pagesViewModel.onSelectedDietitianChanged("$name $surname")
            }
          }
        } catch (e: Exception) {
          Log.e("ShowDropMenuForDietitian", "Error fetching user data", e)
        }
      }
    }
  }
}