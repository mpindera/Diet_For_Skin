package com.example.dietforskin.pages.chat_view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.dietforskin.ui.theme.colorOfBorder
import com.example.dietforskin.viewmodels.ChatViewModel

@Composable
fun ChatPatient(chatViewModel: ChatViewModel, navController: NavHostController) {
  Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
    ElevatedCard(
      modifier = Modifier
        .width(200.dp)
        .height(200.dp),
      elevation = CardDefaults.elevatedCardElevation(100.dp),
    ) {
      Column {

      }
    }
  }


}

@Preview(showBackground = true)
@Composable
fun prev() {
  ChatPatient(ChatViewModel(), NavHostController(LocalContext.current))
}