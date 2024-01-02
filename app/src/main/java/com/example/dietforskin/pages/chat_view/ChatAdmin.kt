package com.example.dietforskin.pages.chat_view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.dietforskin.R
import com.example.dietforskin.navigation.Screen
import com.example.dietforskin.ui.theme.colorOfBorder
import com.example.dietforskin.viewmodels.ChatViewModel

@Composable
fun ChatAdmin(chatViewModel: ChatViewModel, navController: NavHostController) {
  chatViewModel.GetPatientsFromDatabaseToDirectDietitian()
  if (chatViewModel.showListOfPatients) {
    LazyColumn {
      items(chatViewModel.patientsAll) {
        ElevatedCard(
          modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(15.dp)
            .border(1.dp, colorOfBorder, shape = RoundedCornerShape(12.dp))
        ) {
          Box(
            modifier = Modifier.fillMaxSize()
          ) {
            Row(modifier = Modifier.align(Alignment.CenterEnd)) {

              chatViewModel.DividerInChat()

              Box(modifier = Modifier.align(Alignment.CenterVertically)) {

                IconButton(onClick = {
                  navController.navigate(
                    route = Screen.PatientInformation.route.replace(
                      "{uuid}", it.uuid
                    )
                  )
                }) {
                  Icon(
                    painter = painterResource(id = R.drawable.baseline_edit_24),
                    contentDescription = null,
                    modifier = Modifier
                      .width(20.dp)
                      .height(20.dp)
                  )
                }
              }

              chatViewModel.DividerInChat()

              Box(modifier = Modifier.align(Alignment.CenterVertically)) {
                IconButton(onClick = {

                }) {
                  Icon(
                    painter = painterResource(id = R.drawable.baseline_message_24),
                    contentDescription = null,
                    modifier = Modifier
                      .width(20.dp)
                      .height(20.dp)
                  )
                }
              }
            }
            Row {
              Text(
                text = "${it.name} ${it.surname}", modifier = Modifier
                  .align(Alignment.Top)
                  .padding(5.dp)
              )
            }
            Text(
              text = it.email,
              fontSize = 10.sp,
              modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(5.dp)
            )
          }
        }
      }
    }
  }
}