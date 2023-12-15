package com.example.dietforskin.bars.topbar


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.dietforskin.R
import com.example.dietforskin.ui.theme.colorCardIngredient
import com.example.dietforskin.ui.theme.fontFamilyTitle
import com.example.dietforskin.viewmodels.MainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarView(mainViewModel: MainViewModel, navController: NavHostController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    TopAppBar(title = {
        Text(
            text = stringResource(id = R.string.app_name),
            fontFamily = fontFamilyTitle,
            letterSpacing = 1.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )


    }, navigationIcon = {
        if (mainViewModel.isAdminLogged || mainViewModel.isPatientLogged) {
            IconButton(onClick = {
                mainViewModel.showListOfPatients = true
            }) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = null)
            }
        }
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

        AlertdialogToLogout(mainViewModel = mainViewModel, navController = navController)
    }
    if (mainViewModel.showListOfPatients) {
        val scope = rememberCoroutineScope()
        scope.launch {
            drawerState.open()
        }
    }
}

@Composable
fun a() {

}