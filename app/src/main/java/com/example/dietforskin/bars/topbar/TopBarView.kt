package com.example.dietforskin.bars.topbar


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.dietforskin.R
import com.example.dietforskin.data.profile.PagesSite
import com.example.dietforskin.navigation.Screen
import com.example.dietforskin.navigation.ScreensBottomBar
import com.example.dietforskin.pages.patient_information_view.UpdatePatientInformation
import com.example.dietforskin.ui.theme.colorCardIngredient
import com.example.dietforskin.ui.theme.fontFamilyTitle
import com.example.dietforskin.viewmodels.AnimatedSplashScreenViewModel
import com.example.dietforskin.viewmodels.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarView(
    profileViewModel: ProfileViewModel,
    navController: NavHostController,
    animatedSplashScreenViewModel: AnimatedSplashScreenViewModel
) {

    TopAppBar(title = {
        Text(
            text = stringResource(id = R.string.app_name),
            fontFamily = fontFamilyTitle,
            letterSpacing = 1.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )


    }, navigationIcon = {
        if (profileViewModel.selectionOfPagesSite == PagesSite.UPDATE_PROFILE) {
            IconButton(onClick = {
               navController.popBackStack()
            }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
            }
        }
    }, actions = {
        if (profileViewModel.isAdminLogged || profileViewModel.isPatientLogged) {
            IconButton(onClick = {
                profileViewModel.showDialog = true

            }) {
                Icon(imageVector = Icons.Filled.ExitToApp, contentDescription = null)
            }
        }
    }, colors = TopAppBarDefaults.smallTopAppBarColors(
        containerColor = colorCardIngredient
    )
    )
    if (profileViewModel.showDialog) {

        AlertdialogToLogout(
            profileViewModel = profileViewModel,
            navController = navController,
            animatedSplashScreenViewModel = animatedSplashScreenViewModel
        )
    }
}

@Composable
fun a() {

}