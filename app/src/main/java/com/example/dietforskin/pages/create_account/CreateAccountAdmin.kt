package com.example.dietforskin.pages.create_account

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dietforskin.pages.CommonElements
import com.example.dietforskin.viewmodels.PagesViewModel

@Composable
fun CreateAccountAdmin(pagesViewModel: PagesViewModel) {
    val username by pagesViewModel.username.collectAsState()
    val email by pagesViewModel.email.collectAsState()
    Column {
        CustomTextFieldUsername(
            username = username, onValueChangeUsername = pagesViewModel::onUsernameChanged
        )

        Spacer(modifier = Modifier.padding(12.dp))

        CommonElements().CustomTextFieldEmail(
            email = email, onValueChangeEmail = pagesViewModel::onEmailChanged
        )
    }

}