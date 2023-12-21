package com.example.dietforskin.pages.profile_view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.dietforskin.R
import com.example.dietforskin.bars.bottombar.BottomBarView
import com.example.dietforskin.bars.topbar.TopBarView
import com.example.dietforskin.elements.CustomTextField
import com.example.dietforskin.pages.CommonElements
import com.example.dietforskin.ui.theme.colorCircle
import com.example.dietforskin.ui.theme.colorPinkMain
import com.example.dietforskin.ui.theme.colorTextFieldsAndButton
import com.example.dietforskin.viewmodels.AnimatedSplashScreenViewModel
import com.example.dietforskin.viewmodels.ProfileViewModel

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun previewProfileView(){
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var visibilityOfPassword by remember {
        mutableStateOf(true)
    }
    val icon = if (visibilityOfPassword) {
        painterResource(id = R.drawable.baseline_visibility_24)
    } else {
        painterResource(id = R.drawable.baseline_visibility_off_24)
    }
    val navController = rememberNavController()
    Surface(
        modifier = Modifier.fillMaxSize(), color = colorPinkMain
    ) {
        Scaffold(topBar = {
            TopBarView(ProfileViewModel(), navController, AnimatedSplashScreenViewModel())
        }, modifier = Modifier.fillMaxSize(), bottomBar = {
            BottomBarView(navController = navController, profileViewModel = ProfileViewModel())
        }) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                CommonElements().CanvasBackground(modifier = Modifier.align(alignment = Alignment.TopEnd))
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(start = 40.dp, end = 40.dp)
                ) {
                    Column(modifier = Modifier.padding(15.dp)) {

                        CustomTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = {
                                Text(text = ("EMAIL"), letterSpacing = 1.sp)
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        )

                        Spacer(modifier = Modifier.padding(12.dp))

                        CustomTextField(value = password,
                            onValueChange = { password = it },
                            label = {
                                Text(text = stringResource(id = R.string.password), letterSpacing = 1.sp)
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            visualTransformation = if (visibilityOfPassword) {
                                PasswordVisualTransformation()
                            } else {
                                VisualTransformation.None
                            },
                            trailingIcon = {
                                IconButton(onClick = {
                                    visibilityOfPassword = !visibilityOfPassword
                                }) {
                                    Icon(
                                        icon, contentDescription = "Show Icon"
                                    )
                                }
                            })
                        ElevatedButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 20.dp),
                            onClick = {

                            },
                            shape = RoundedCornerShape(0.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorTextFieldsAndButton,
                                contentColor = Color.Black
                            ),
                            elevation = ButtonDefaults.elevatedButtonElevation(15.dp)
                        ) {
                            Text(text = stringResource(id = R.string.login_in), letterSpacing = 1.sp)
                        }

                        ElevatedButton(
                            modifier = Modifier
                                .align(Alignment.End)
                                .padding(top = 35.dp),
                            onClick = {

                            },
                            shape = RoundedCornerShape(0.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorTextFieldsAndButton,
                                contentColor = Color.Black
                            ),
                            elevation = ButtonDefaults.elevatedButtonElevation(15.dp)
                        ) {
                            Text(text = "SIGN OUT", letterSpacing = 1.sp)
                        }
                        Text(
                            modifier = Modifier
                                .align(Alignment.Start)
                                .clickable {
                                    //TODO
                                }, text = stringResource(id = R.string.reset_Password), fontStyle = FontStyle.Italic
                        )
                    }
                }
            }
        }
    }
}