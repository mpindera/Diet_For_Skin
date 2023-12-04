package com.example.dietforskin.bars.topbar


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.dietforskin.R
import com.example.dietforskin.data.auth.PagesToRoles
import com.example.dietforskin.data.profile.PagesSite
import com.example.dietforskin.ui.theme.colorCardIngredient
import com.example.dietforskin.ui.theme.fontFamilyTitle
import com.example.dietforskin.viewmodels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarView(mainViewModel: MainViewModel) {
    TopAppBar(title = {
        Text(
            text = stringResource(id = R.string.app_name),
            fontFamily = fontFamilyTitle,
            letterSpacing = 1.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )


    }, navigationIcon = {

    }, actions = {
        if (mainViewModel.isAdminLogged || mainViewModel.isPatientLogged) {
            IconButton(onClick = {
                mainViewModel.showDialog = true

            }) {
                Icon(imageVector = Icons.Filled.ExitToApp, contentDescription = null)
            }
        }
    }, colors = TopAppBarDefaults.smallTopAppBarColors(
        containerColor = colorCardIngredient
    )
    )
    if (mainViewModel.showDialog) {
        AlertdialogToLogout(mainViewModel = mainViewModel)
    }
}
