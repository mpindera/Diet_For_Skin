package com.example.dietforskin.topbar


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import com.example.dietforskin.data.profile.PagesSite
import com.example.dietforskin.ui.theme.colorCardIngredient
import com.example.dietforskin.viewmodels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarView(mainViewModel: MainViewModel) {
  val profileSite = mainViewModel.selectionOfPagesSite == PagesSite.PROFILE_VIEW
  TopAppBar(
    title = {
      Text(
        text = "Diet for skin",
        fontStyle = FontStyle.Italic,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
      )

    },
    navigationIcon = {

    },
    actions = {

    },
    colors = TopAppBarDefaults.smallTopAppBarColors(
      containerColor = colorCardIngredient
    )
  )
}
