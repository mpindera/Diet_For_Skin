package com.example.dietforskin.pages.chat_view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.dietforskin.R
import com.example.dietforskin.navigation.Screen
import com.example.dietforskin.pages.CommonElements
import com.example.dietforskin.ui.theme.colorCardIngredient
import com.example.dietforskin.ui.theme.colorOfBorder
import com.example.dietforskin.ui.theme.colorPinkMain
import com.example.dietforskin.viewmodels.ChatViewModel


@Composable
fun ChatPatient(chatViewModel: ChatViewModel, navController: NavHostController) {

  chatViewModel.GetDietitianFromDatabaseToDirectPatient()

  Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
    ElevatedCard(
      modifier = Modifier
        .width(270.dp)
        .height(170.dp),
      elevation = CardDefaults.elevatedCardElevation(120.dp),
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 5.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Column(
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text(
            text = "${stringResource(id = R.string.dietitian)}: ",
            letterSpacing = 1.sp,
            modifier = Modifier.padding(top = 5.dp)
          )
          Text(
            text = chatViewModel.dietitian,
            modifier = Modifier.padding(top = 5.dp, bottom = 8.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
          )

          Divider(thickness = 1.7.dp)

          Row(
            modifier = Modifier
              .fillMaxSize()
              .padding(10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Button(
              onClick = {},
              colors = ButtonDefaults.buttonColors(colorPinkMain),
            ) {
              Row(Modifier.padding(5.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                Icon(
                  painter = painterResource(id = R.drawable.baseline_list_24),
                  contentDescription = null,
                  modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
                    .padding(4.dp)
                )
                Text(text = stringResource(id = R.string.files), color = Color.Black)
              }
            }

            Box(modifier = Modifier.padding(start = 5.dp, end = 5.dp)) {

            }

            Button(
              onClick = {},
              colors = ButtonDefaults.buttonColors(colorPinkMain),
            ) {
              Row(Modifier.padding(5.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                Icon(
                  painter = painterResource(id = R.drawable.baseline_message_24),
                  contentDescription = null,
                  modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
                    .padding(4.dp)
                )
                Text(text = stringResource(id = R.string.chat), color = Color.Black)
              }
            }

          }
        }
      }
    }
  }
}


@Preview(showBackground = true)
@Composable
fun prev() {
  val chatViewModel = ChatViewModel()
  chatViewModel.addDietitian("Mikołaj Majkołowski")
  ChatPatient(chatViewModel, NavHostController(LocalContext.current))

}