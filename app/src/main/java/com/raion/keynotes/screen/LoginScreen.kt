package com.raion.keynotes.screen

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.raion.keynotes.R
import com.raion.keynotes.component.BarButton
import com.raion.keynotes.component.RaionTextField
import com.raion.keynotes.model.TokenClass
import com.raion.keynotes.navigation.NavEnum
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun LoginScreen(
    navController: NavController,
    postRegister: (List<String>) -> Unit,
    postLogin: (List<String>) -> Unit
){
    val context = LocalContext.current as Activity

    var loginFlag = remember {
        mutableStateOf("1")
    }
    var preventFlag = remember {
        mutableStateOf(false)
    }
    var name = remember {
        mutableStateOf("")
    }
    var nim = remember {
        mutableStateOf("")
    }
    var password = remember {
        mutableStateOf("")
    }
    var description = remember {
        mutableStateOf("")
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(30,30,30, 255)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .height(90.dp)
                    .width(90.dp)
            ) {
                Surface(modifier = Modifier.fillMaxSize(), color = Color.Transparent) {
                    Image(
                        painter = painterResource(id = R.drawable.raion),
                        contentDescription = "raion",
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(
                        if (loginFlag.value == "1") {
                            230.dp
                        } else {
                            370.dp
                        }
                    ),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(Color(51, 47, 51)),
                elevation = CardDefaults.cardElevation(5.dp),
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.clickable { loginFlag.value = "1" },
                            text = "Login",
                            fontSize = 20.sp,
                            color =
                            if (loginFlag.value == "1") {
                                Color(255, 199, 0, 255)
                            } else {
                                Color.White
                            },
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.padding(10.dp))
                        Text(
                            modifier = Modifier.clickable { loginFlag.value = "2" },
                            text = "Register",
                            fontSize = 20.sp,
                            color =
                            if (loginFlag.value == "2") {
                                Color(255, 199, 0, 255)
                            } else {
                                Color.White
                            },
                            fontWeight =
                            FontWeight.SemiBold
                        )
                    }
                    Spacer(modifier = Modifier.padding(4.dp))
                    if (loginFlag.value == "2") {
                        RaionTextField(
                            keyboardType = KeyboardType.Text,
                            text = name.value, label = "Name",
                            onTextChange = {
                                if ((it.all { char -> char.isDefined() || char.isWhitespace() })) {
                                    name.value = it
                                    preventFlag.value = false
                                }
                            }
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                    }
                    RaionTextField(
                        keyboardType = KeyboardType.Text,
                        text = nim.value, label = "NIM",
                        onTextChange = {
                            if ((it.all { char -> char.isDefined() || char.isWhitespace() })) {
                                nim.value = it
                                preventFlag.value = false
                            }
                        }
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    RaionTextField(
                        keyboardType = KeyboardType.Text,
                        text = password.value, label = "Password",
                        onTextChange = {
                            if ((it.all { char -> char.isDefined() || char.isWhitespace() })) {
                                password.value = it
                                preventFlag.value = false
                            }
                        }
                    )
                    Spacer(modifier = Modifier.padding(3.dp))
                    if (loginFlag.value == "2") {
                        RaionTextField(
                            keyboardType = KeyboardType.Text,
                            text = description.value, label = "Description",
                            onTextChange = {
                                if ((it.all { char -> char.isDefined() || char.isWhitespace() })) {
                                    description.value = it
                                    preventFlag.value = false
                                }
                            }
                        )
                    }
                    Spacer(modifier = Modifier.padding(4.dp))
                    if (loginFlag.value == "1") {
                        if (nim.value.equals("") || password.value.equals("")) {
                            preventFlag.value = true
                        }
                    } else {
                        if (name.value.equals("") || nim.value.equals("") || password.value.equals("") || description.value.equals(
                                ""
                            )
                        ) {
                            preventFlag.value = true
                        }
                    }

                }
            }
            Spacer(modifier = Modifier.padding(8.dp))
            BarButton(
                text = "Continue",
                color =
                if (preventFlag.value == false) {
                    Color(255, 199, 0, 255)
                } else {
                    Color(101, 100, 102)
                }
            ) {
                if (preventFlag.value == false) {
                    if(loginFlag.value == "1"){
                        postLogin(listOf(nim.value, password.value))
                        postLogin(listOf(nim.value, password.value))

                        nim.value = ""
                        password.value = ""
                        navController.navigate(route = NavEnum.HomeScreen.name)
                        context.recreate()
                    } else {
                        postRegister(listOf(name.value, nim.value, password.value, description.value))
                        name.value = ""
                        nim.value = ""
                        password.value = ""
                        description.value = ""

                        context.recreate()
                    }
                }
            }
            Spacer(modifier = Modifier.padding(80.dp))
        }
    }
}