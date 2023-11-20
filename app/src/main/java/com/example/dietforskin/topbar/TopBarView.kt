package com.example.dietforskin.topbar


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.dietforskin.ui.theme.colorCardIngredient
import com.example.dietforskin.ui.theme.colorPinkMain

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarView() {
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
