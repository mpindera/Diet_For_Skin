package com.example.dietforskin.pages.chat_view

import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.dietforskin.viewmodels.ChatViewModel

@Composable
fun Chat(navController: NavHostController, context: Context, chatViewModel: ChatViewModel) {

    chatViewModel.GetPatientsFromDatabaseToDirectDietitian()

    if (chatViewModel.showListOfPatients) {
        LazyColumn {
            items(chatViewModel.patientsAll) {
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

                    Text(
                        text = it.username,
                        modifier = Modifier
                            .align(CenterHorizontally)
                            .padding(5.dp)
                    )
                    Text(
                        text = it.email, modifier = Modifier
                            .align(CenterHorizontally)
                            .padding(5.dp)
                    )
                }
            }
        }
    }
}

