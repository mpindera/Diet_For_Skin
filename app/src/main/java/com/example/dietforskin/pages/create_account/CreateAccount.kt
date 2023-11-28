package com.example.dietforskin.pages.create_account

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Snackbar
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.dietforskin.R
import com.example.dietforskin.bottombar.BottomBarView
import com.example.dietforskin.data.profile.person.Person
import com.example.dietforskin.elements.CustomTextFieldLogin
import com.example.dietforskin.topbar.TopBarView
import com.example.dietforskin.ui.theme.colorCircle
import com.example.dietforskin.ui.theme.colorPinkMain
import com.example.dietforskin.ui.theme.colorTextFieldsAndButton
import com.example.dietforskin.viewmodels.MainViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAccount(mainViewModel: MainViewModel, navController: NavHostController) {
  val db = Firebase.firestore
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
  var selectedRole by remember { mutableStateOf("ROLE") }
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

        CustomTextFieldLogin(
          value = password,
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
              db.collection("users").add(
                Person(
                  username = username,
                  password = password,
                  email = email,
                  role = selectedRole
                )
              ).addOnSuccessListener {

              }.addOnFailureListener{
                //todo that
              }
            },
            shape = RoundedCornerShape(0.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = colorTextFieldsAndButton,
              contentColor = Color.Black
            ),
            elevation = ButtonDefaults.elevatedButtonElevation(15.dp)
          ) {
            Text(text = "CREATE", letterSpacing = 1.sp)
          }
        }
        ElevatedButton(
          modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth(),
          onClick = {
            password = generateRandom()
          },
          shape = RoundedCornerShape(0.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = colorTextFieldsAndButton,
            contentColor = Color.Black
          ),
          elevation = ButtonDefaults.elevatedButtonElevation(15.dp)
        ) {
          Text(text = "GENERATE PASSWORD", letterSpacing = 1.sp)
        }
      }
    }
  }
}

@Composable
fun ShowDropMenu(isFold: Boolean, onRoleSelected: (String) -> Unit) {
  DropdownMenu(
    expanded = isFold, onDismissRequest = { !isFold }, modifier = Modifier
      .width(100.dp)
      .height(100.dp)
  ) {
    DropdownMenuItem(text = { Text("Admin") }, onClick = {
      onRoleSelected("Admin")
    })
    DropdownMenuItem(text = { Text("Patient") }, onClick = {
      onRoleSelected("Patient")
    })
  }
}

fun generateRandom(): String {
  val first = UUID.randomUUID().toString().subSequence(0, 8)
  val second = UUID.randomUUID().toString().subSequence(9, 12)
  return "$first$second"
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
  var selectedRole by remember { mutableStateOf("ROLE") }
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
    Scaffold(topBar = {}, modifier = Modifier.fillMaxSize(), bottomBar = {
      BottomBarView(navController = navController, mainViewModel = MainViewModel())
    }) { paddingValues ->
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(paddingValues)
      ) {
        Spacer(modifier = Modifier.border(1.dp, Color.Black))
        Canvas(modifier = Modifier.align(alignment = Alignment.TopEnd),
          onDraw = {
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

            CustomTextFieldLogin(
              value = password,
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
                  //TODO
                },
                shape = RoundedCornerShape(0.dp),
                colors = ButtonDefaults.buttonColors(
                  containerColor = colorTextFieldsAndButton,
                  contentColor = Color.Black
                ),
                elevation = ButtonDefaults.elevatedButtonElevation(15.dp)
              ) {
                Text(text = "CREATE", letterSpacing = 1.sp)
              }
            }

            ElevatedButton(
              modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth(),
              onClick = {
                println(generateRandom())
              },
              shape = RoundedCornerShape(0.dp),
              colors = ButtonDefaults.buttonColors(
                containerColor = colorTextFieldsAndButton,
                contentColor = Color.Black
              ),
              elevation = ButtonDefaults.elevatedButtonElevation(15.dp)
            ) {
              Text(text = "GENERATE PASSWORD", letterSpacing = 1.sp)
            }
          }
        }
      }
    }
  }
}




