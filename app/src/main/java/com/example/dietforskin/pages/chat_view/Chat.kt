package com.example.dietforskin.pages.chat_view

import android.content.Context
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
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.dietforskin.R
import com.example.dietforskin.data.profile.PagesSite
import com.example.dietforskin.navigation.Screen
import com.example.dietforskin.ui.theme.colorOfBorder
import com.example.dietforskin.viewmodels.ChatViewModel
import com.example.dietforskin.viewmodels.ProfileViewModel

@Composable
fun Chat(
  navController: NavHostController,
  context: Context,
  chatViewModel: ChatViewModel,
  profileViewModel: ProfileViewModel
) {
  profileViewModel.updateSelectionOfPagesSite(PagesSite.CHAT_VIEW)


  chatViewModel.checkIfAdminOrPatient()

}


