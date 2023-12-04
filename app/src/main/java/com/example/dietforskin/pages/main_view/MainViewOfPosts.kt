package com.example.dietforskin.pages.main_view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.dietforskin.viewmodels.MainViewModel

@Composable
fun MainViewOfPosts(mainViewModel: MainViewModel) {
    mainViewModel.onShowBarChanged(true)
   Column(
       modifier = Modifier.fillMaxSize()
   ) {
       Text(text = "Main")
   }
}