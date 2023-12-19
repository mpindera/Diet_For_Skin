package com.example.dietforskin.pages.create_account

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dietforskin.ui.theme.colorTextFieldsAndButton
import com.example.dietforskin.viewmodels.PagesViewModel

@Composable
fun CreateAccountRole(pagesViewModel: PagesViewModel) {
    val role by pagesViewModel.selectedRole.collectAsState()

    var isFold by remember {
        mutableStateOf(false)
    }
    Box(modifier = Modifier.fillMaxWidth()) {
        ElevatedButton(
            modifier = Modifier
                .padding(
                    top = 20.dp
                )
                .align(pagesViewModel.modif(pagesViewModel.visibilityOfAnimation)),
            onClick = {
                isFold = !isFold

            },
            shape = RoundedCornerShape(0.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorTextFieldsAndButton,
                contentColor = Color.Black
            ),
            elevation = ButtonDefaults.elevatedButtonElevation(15.dp)
        ) {
            Text(text = role, letterSpacing = 1.sp)
        }
        Column(
            modifier = Modifier
                .padding(
                    top = 20.dp
                )
                .align(Alignment.BottomStart)
        ) {
            if (isFold) {
                ShowDropMenu(isFold = isFold, onRoleSelected = {
                    pagesViewModel.onSelectedRoleChanged(it);
                    isFold = false
                })
            }
        }
    }
}