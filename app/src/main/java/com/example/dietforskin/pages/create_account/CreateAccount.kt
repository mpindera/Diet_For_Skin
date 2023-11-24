package com.example.dietforskin.pages.create_account

import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.dietforskin.R
import com.example.dietforskin.bottombar.BottomBarView
import com.example.dietforskin.data.auth.AuthRepository
import com.example.dietforskin.data.auth.AuthRepositoryImpl
import com.example.dietforskin.data.auth.PagesToRoles
import com.example.dietforskin.elements.CustomTextFieldLogin
import com.example.dietforskin.topbar.TopBarView
import com.example.dietforskin.ui.theme.colorCircle
import com.example.dietforskin.ui.theme.colorPinkMain
import com.example.dietforskin.ui.theme.colorTextFieldsAndButton
import com.example.dietforskin.viewmodels.AuthManager
import com.example.dietforskin.viewmodels.MainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAccount(mainViewModel: MainViewModel, navController: NavHostController) {
    var username by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var visibilityOfPassword by remember {
        mutableStateOf(true)
    }
    var isFold by remember {
        mutableStateOf(false)
    }
    var selectedRole by remember { mutableStateOf("") }
    val icon = if (visibilityOfPassword) {
        painterResource(id = R.drawable.baseline_visibility_24)
    } else {
        painterResource(id = R.drawable.baseline_visibility_off_24)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding()
    ) {
        Spacer(modifier = Modifier.border(1.dp, Color.Black))
        Canvas(modifier = Modifier.align(alignment = Alignment.TopEnd), onDraw = {
            drawCircle(
                color = colorCircle, radius = 450.dp.toPx()
            )
        })

        Box {
            Canvas(modifier = Modifier.align(alignment = Alignment.TopStart), onDraw = {
                drawCircle(
                    color = colorPinkMain, radius = 140.dp.toPx()
                )
            })
            Text(
                text = "CREATE\nACCOUNT",
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 11.dp, top = 26.dp),
                letterSpacing = 1.sp,
                textAlign = TextAlign.Start
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(start = 40.dp, end = 40.dp)
        ) {
            Column(modifier = Modifier.padding(15.dp)) {

                CustomTextFieldLogin(
                    value = username,
                    onValueChange = { username = it },
                    label = {
                        Text(text = ("USERNAME"), letterSpacing = 1.sp)
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                )

                Spacer(modifier = Modifier.padding(12.dp))

                CustomTextFieldLogin(
                    value = email,
                    onValueChange = { email = it },
                    label = {
                        Text(text = ("EMAIL"), letterSpacing = 1.sp)
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                )

                Spacer(modifier = Modifier.padding(12.dp))

                CustomTextFieldLogin(value = password,
                    onValueChange = { password = it },
                    label = {
                        Text(text = ("PASSWORD"), letterSpacing = 1.sp)
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),

                    trailingIcon = {
                        IconButton(onClick = {
                            visibilityOfPassword = !visibilityOfPassword
                        }) {
                            Icon(
                                icon, contentDescription = "Show Icon"
                            )
                        }
                    })
                Row(modifier = Modifier.fillMaxWidth()) {
                    ElevatedButton(
                        modifier = Modifier
                            .padding(
                                top = 20.dp
                            ),
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
                        Text(text = "ROLE", letterSpacing = 1.sp)
                    }

                    if (isFold) {
                        ShowDropMenu(isFold = isFold,
                            onRoleSelected = { selectedRole = it; isFold = false })
                    }

                    ElevatedButton(
                        modifier = Modifier
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
                        Text(text = "CREATE ACCOUNT", letterSpacing = 1.sp)
                    }

                }
            }
        }
    }
}

@Composable
fun ShowDropMenu(isFold: Boolean, onRoleSelected: (String) -> Unit) {
    DropdownMenu(
        expanded = isFold, onDismissRequest = { }, modifier = Modifier
            .width(200.dp)
            .height(100.dp)
    ) {
        DropdownMenuItem(text = { Text("a") }, onClick = {
            onRoleSelected("Admin")
        })
        DropdownMenuItem(text = { Text("a") }, onClick = {
            onRoleSelected("Patient")
        })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun prev() {
    var username by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var visibilityOfPassword by remember {
        mutableStateOf(true)
    }
    var selectedRole by remember { mutableStateOf("") }
    var isFold by remember {
        mutableStateOf(false)
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
            TopBarView()
        }, modifier = Modifier.fillMaxSize(), bottomBar = {
            BottomBarView(navController = navController, mainViewModel = MainViewModel())
        }) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Spacer(modifier = Modifier.border(1.dp, Color.Black))
                Canvas(modifier = Modifier.align(alignment = Alignment.TopEnd), onDraw = {
                    drawCircle(
                        color = colorCircle, radius = 450.dp.toPx()
                    )
                })

                Box {
                    Canvas(modifier = Modifier.align(alignment = Alignment.TopStart), onDraw = {
                        drawCircle(
                            color = colorPinkMain, radius = 140.dp.toPx()
                        )
                    })
                    Text(
                        text = "CREATE\nACCOUNT",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(start = 11.dp, top = 26.dp),
                        letterSpacing = 1.sp,
                        textAlign = TextAlign.Start
                    )
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(start = 40.dp, end = 40.dp)
                ) {
                    Column(modifier = Modifier.padding(15.dp)) {

                        CustomTextFieldLogin(
                            value = username,
                            onValueChange = { username = it },
                            label = {
                                Text(text = ("USERNAME"), letterSpacing = 1.sp)
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        )

                        Spacer(modifier = Modifier.padding(12.dp))

                        CustomTextFieldLogin(
                            value = email,
                            onValueChange = { email = it },
                            label = {
                                Text(text = ("EMAIL"), letterSpacing = 1.sp)
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        )

                        Spacer(modifier = Modifier.padding(12.dp))

                        CustomTextFieldLogin(value = password,
                            onValueChange = { password = it },
                            label = {
                                Text(text = ("PASSWORD"), letterSpacing = 1.sp)
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),

                            trailingIcon = {
                                IconButton(onClick = {
                                    visibilityOfPassword = !visibilityOfPassword
                                }) {
                                    Icon(
                                        icon, contentDescription = "Show Icon"
                                    )
                                }
                            })
                        Row(modifier = Modifier.fillMaxWidth()) {
                            ElevatedButton(
                                modifier = Modifier
                                    .padding(
                                        top = 20.dp, start = 2.dp, end = 4.dp
                                    ),
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
                                Text(text = "ROLE", letterSpacing = 1.sp)
                            }

                            if (isFold) {
                                ShowDropMenu(isFold = isFold,
                                    onRoleSelected = { selectedRole = it; isFold = false })
                            }

                            ElevatedButton(
                                modifier = Modifier
                                    .padding(top = 20.dp, end = 2.dp, start = 5.dp),
                                onClick = {

                                },
                                shape = RoundedCornerShape(0.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorTextFieldsAndButton,
                                    contentColor = Color.Black
                                ),
                                elevation = ButtonDefaults.elevatedButtonElevation(15.dp)
                            ) {
                                Text(text = "CREATE ACCOUNT", letterSpacing = 1.sp)
                            }

                        }
                    }
                }
            }
        }
    }
}



