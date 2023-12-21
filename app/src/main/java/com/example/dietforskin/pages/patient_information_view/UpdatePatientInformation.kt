package com.example.dietforskin.pages.patient_information_view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dietforskin.R
import com.example.dietforskin.data.profile.PagesSite
import com.example.dietforskin.pages.CommonElements
import com.example.dietforskin.viewmodels.ProfileViewModel

@Composable
fun UpdatePatientInformation(profileViewModel: ProfileViewModel) {
    profileViewModel.updateSelectionOfPagesSite(PagesSite.UPDATE_PROFILE)
    Box(modifier = Modifier.fillMaxWidth()) {
        CommonElements().CanvasBackground(modifier = Modifier.align(alignment = Alignment.TopEnd))
        CommonElements().canvasWithName(label = "Update Profile")

    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(stringResource(id = R.string.email))
                TextField(value = "", onValueChange = { })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun test() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column {
                Text(stringResource(id = R.string.email))
                TextField(value = "", onValueChange = { })
            }
            Spacer(modifier = Modifier.height(55.dp))
            Column {
                Text(stringResource(id = R.string.username))
                TextField(value = "", onValueChange = { })
            }
        }
    }
}