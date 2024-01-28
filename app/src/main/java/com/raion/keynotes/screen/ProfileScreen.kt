package com.raion.keynotes.screen

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.raion.keynotes.R
import com.raion.keynotes.component.BarButton
import com.raion.keynotes.component.RaionTextField
import com.raion.keynotes.model.NoteItem
import com.raion.keynotes.navigation.NavEnum

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: RaionAPIViewModel,
    navController: NavController
) {
    val context = LocalContext.current as Activity
    var displayForm = remember {
        mutableStateOf(false)
    }
    var newUserName = remember {
        mutableStateOf("")
    }
    var newPassword = remember {
        mutableStateOf("")
    }
    var preventFlag = remember {
        mutableStateOf(false)
    }

    var userDetail = viewModel.getUserDetail.value.data

    var userId: String = ""
    var userName: String = ""
    var password: String = ""
    var description: String = ""

    if(userDetail != null){
        userId   = userDetail.data.id
        userName = userDetail.data.name
        password = userDetail.data.password
        description = userDetail.message
    }

    var noteRawList = viewModel.getNote.value.data
    var noteList: List<NoteItem>

    if (noteRawList != null) {
        noteList = noteRawList.data
    } else {
        noteList = emptyList()
    }

    Surface(modifier = Modifier.fillMaxSize(), color = Color(30, 30, 30, 255)) {
        Scaffold(
            content = {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.15f), color = Color(30, 30, 30, 255)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(15.dp)
                            .padding(bottom = 30.dp, end = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            Text(text = "Lorem Ipsum", color = Color.White, fontSize = 15.sp)
                            Spacer(modifier = Modifier.padding(1.dp))
                            Text(text = "Hi, ${userName}👋", fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.SemiBold)
                        }
                        Image(
                            painter = painterResource(id = R.drawable.raion),
                            contentDescription = "Raion",
                            modifier = Modifier.padding(top = 12.dp, start = 5.dp)
                        )
                    }
                }
            },
            bottomBar = {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.883f),
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
                    colors = CardDefaults.cardColors(Color(30, 30, 30, 255)),
                    elevation = CardDefaults.cardElevation(10.dp)
                ) {
                    Scaffold(
                        content = {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(0.92f)
                                    .padding(15.dp)
                                    .padding(top = 22.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Top
                            ) {
                                Text(
                                    text = "Profile",
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.inverseSurface
                                )
                                Spacer(modifier = Modifier.padding(5.dp))

                                if (displayForm.value) {
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(180.dp),
                                        shape = RoundedCornerShape(20.dp),
                                        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
                                        elevation = CardDefaults.cardElevation(5.dp),
                                    ) {
                                        Column(
                                            modifier = Modifier.fillMaxSize(),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            RaionTextField(
                                                keyboardType = KeyboardType.Text,
                                                text = newUserName.value,
                                                label = "Insert New Username",
                                                onTextChange = {
                                                    if ((it.all { char -> char.isDefined() || char.isWhitespace() })) {
                                                        newUserName.value = it
                                                        preventFlag.value = false
                                                    }
                                                }
                                            )
                                            Spacer(modifier = Modifier.padding(5.dp))
                                            RaionTextField(
                                                keyboardType = KeyboardType.Text,
                                                text = newPassword.value,
                                                label = "Insert New Password",
                                                onTextChange = {
                                                    if ((it.all { char -> char.isDefined() || char.isWhitespace() })) {
                                                        newPassword.value = it
                                                    }
                                                }
                                            )
                                            Spacer(modifier = Modifier.padding(3.dp))
                                            if (newUserName.value.equals("") || newPassword.value.equals("")) {
                                                preventFlag.value = true
                                            }
                                        }
                                    }

                                    Spacer(modifier = Modifier.padding(8.dp))
                                    BarButton(
                                        text = "Update Profile",
                                        color =
                                        //if (preventFlag.value == false) {
                                        //    Color(255, 199, 0, 255)
                                        //} else {
                                            Color(101, 100, 102)
                                        //}
                                    ) {
                                        //if (preventFlag.value == false) {
                                        //    addNote(
                                        //        Pair(
                                        //            newNoteTitle.value,
                                        //            newNoteDescription.value
                                        //        )
                                        //    )
                                        //    newNoteTitle.value = ""
                                        //    newNoteDescription.value = ""
                                        //    displayForm.value = !displayForm.value

                                        //}
                                    }

                                } else {
                                    Spacer(modifier = Modifier.padding(5.dp))
                                    Card(
                                        modifier = Modifier
                                            .width(140.dp)
                                            .height(140.dp),
                                        shape = CircleShape,
                                        elevation = CardDefaults.cardElevation(8.dp)
                                    ){
                                        Surface(modifier = Modifier.fillMaxSize()) {
                                            Image(painter = painterResource(id = R.drawable.cryingcat), contentDescription = "cat", contentScale = ContentScale.Crop)
                                        }
                                    }
                                    Spacer(modifier = Modifier.padding(10.dp))
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(140.dp),
                                        elevation = CardDefaults.cardElevation(8.dp)
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(start = 15.dp, end = 15.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ){
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(40.dp),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(text = "Username:", fontSize = 16.sp)
                                                Text(text = userName, fontSize = 16.sp, maxLines = 1)
                                            }
                                            Divider(thickness = 2.dp)
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(40.dp),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(text = "NIM:", fontSize = 16.sp)
                                                Text(text = userId, fontSize = 16.sp, maxLines = 1)
                                            }
                                            Divider(thickness = 2.dp)
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(40.dp),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(text = "Password:     ", fontSize = 16.sp)
                                                Text(text = password, fontSize = 16.sp, maxLines = 1)
                                            }
                                        }
                                    }
                                    Spacer(modifier = Modifier.padding(7.dp))
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(100.dp),
                                        elevation = CardDefaults.cardElevation(8.dp)
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(start = 15.dp, end = 15.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(40.dp),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(text = "Total Notes:", fontSize = 16.sp)
                                                Text(text = noteList.size.toString(), fontSize = 16.sp)
                                            }
                                            Divider(thickness = 2.dp)
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(40.dp),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(text = "Last Activity:", fontSize = 16.sp)
                                                Text(
                                                    text =
                                                        if(noteList.size > 0){
                                                            noteList[noteList.lastIndex].updatedAt
                                                        } else {
                                                            "none"
                                                        }
                                                    ,
                                                    fontSize = 13.sp
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        },
                        bottomBar = {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(85.dp)
                                    .padding(start = 15.dp, end = 15.dp)
                            ) {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(65.dp),
                                    shape = RoundedCornerShape(30.dp),
                                    elevation = CardDefaults.cardElevation(10.dp),
                                    colors = CardDefaults.cardColors(Color(51, 47, 51))
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalArrangement = Arrangement.SpaceEvenly,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .width(85.dp)
                                                .height(75.dp)
                                                .padding(17.dp), contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.download),
                                                contentDescription = "home",
                                                tint = Color(255, 199, 0, 255),
                                                modifier = Modifier.clickable { navController.navigate(route = NavEnum.DownloadScreen.name)}
                                            )
                                        }

                                        Box(
                                            modifier = Modifier
                                                .width(85.dp)
                                                .height(75.dp)
                                                .padding(16.dp)
                                                .padding(top = 3.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.note),
                                                contentDescription = "home",
                                                tint = Color(255, 199, 0, 255),
                                                modifier = Modifier.clickable { navController.navigate(route = NavEnum.HomeScreen.name) }
                                            )
                                        }

                                        Card(
                                            modifier = Modifier
                                                .width(85.dp)
                                                .height(75.dp),
                                            shape = CircleShape,
                                            colors = CardDefaults.cardColors(
                                                Color(
                                                    30,
                                                    30,
                                                    30,
                                                    255
                                                )
                                            ),
                                            elevation = CardDefaults.cardElevation(5.dp),
                                            border = BorderStroke(3.dp, Color(255, 199, 0, 255))

                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(16.dp)
                                                    .padding(top = 2.dp),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Icon(
                                                    painter = painterResource(id = R.drawable.profile),
                                                    contentDescription = "home",
                                                    tint = Color(255, 199, 0, 255)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        },
                        floatingActionButton = {
                            Card(
                                modifier = Modifier
                                    .width(65.dp)
                                    .height(65.dp)
                                    .clickable { displayForm.value = !displayForm.value },
                                shape = CircleShape,
                                colors = CardDefaults.cardColors(Color(51, 47, 51)),
                                elevation = CardDefaults.cardElevation(10.dp),
                                border = BorderStroke(3.dp, color = Color(255, 199, 0, 255))
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(15.dp)
                                        , contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Create,
                                        contentDescription = "new pocket",
                                        tint = Color(255, 199, 0, 255),
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            }
                        }
                    )
                }
            },
        )
    }
}