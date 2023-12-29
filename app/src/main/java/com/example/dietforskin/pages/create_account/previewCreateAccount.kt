package com.example.dietforskin.pages.create_account

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.dietforskin.R
import com.example.dietforskin.bars.bottombar.BottomBarView
import com.example.dietforskin.elements.CustomTextField
import com.example.dietforskin.pages.CommonElements
import com.example.dietforskin.ui.theme.colorCircle
import com.example.dietforskin.ui.theme.colorPinkMain
import com.example.dietforskin.ui.theme.colorTextFieldsAndButton
import com.example.dietforskin.viewmodels.MainViewModel
import com.example.dietforskin.viewmodels.ProfileViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun previewCreateAccount() {
    var username by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var selectedRole by remember { mutableStateOf("ROLE") }

    var isFold by remember {
        mutableStateOf(false)
    }

    val navController = rememberNavController()
    Surface(
        modifier = Modifier.fillMaxSize(), color = colorPinkMain
    ) {
        Scaffold(topBar = {}, modifier = Modifier.fillMaxSize(), bottomBar = {
            BottomBarView(navController = navController, profileViewModel = ProfileViewModel())
        }) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                CommonElements().CanvasBackground(modifier = Modifier.align(alignment = Alignment.TopEnd))

                CommonElements().canvasWithName(stringResource(id = R.string.create_account))

                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(start = 40.dp, end = 40.dp)
                ) {
                    Column(modifier = Modifier.padding(15.dp)) {

                        CustomTextField(
                            value = username,
                            onValueChange = { username = it },
                            label = {
                                Text(text = stringResource(id = R.string.name), letterSpacing = 1.sp)
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        )

                        Spacer(modifier = Modifier.padding(12.dp))

                        CustomTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = {
                                Text(text = ("EMAIL"), letterSpacing = 1.sp)
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        )

                        Spacer(modifier = Modifier.padding(12.dp))

                        Box(modifier = Modifier.fillMaxWidth()) {
                            ElevatedButton(
                                modifier = Modifier
                                    .padding(
                                        top = 20.dp
                                    )
                                    .align(Alignment.CenterStart),
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
                                Text(text = selectedRole, letterSpacing = 1.sp)
                            }

                            if (isFold) {
                                ShowDropMenu(isFold = isFold,
                                    onRoleSelected = { selectedRole = it; isFold = false })
                            }

                            ElevatedButton(
                                modifier = Modifier
                                    .padding(top = 20.dp)
                                    .align(Alignment.CenterEnd),
                                onClick = {
                                    // Sending to DB
                                },
                                shape = RoundedCornerShape(0.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorTextFieldsAndButton,
                                    contentColor = Color.Black
                                ),
                                elevation = ButtonDefaults.elevatedButtonElevation(15.dp)
                            ) {
                                Text(text = stringResource(id = R.string.create), letterSpacing = 1.sp)
                            }
                        }

                    }
                }
            }
        }
    }
}
