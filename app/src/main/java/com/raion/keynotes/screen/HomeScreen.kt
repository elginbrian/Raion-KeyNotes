package com.raion.keynotes.screen

import android.annotation.SuppressLint
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.raion.keynotes.R
import com.raion.keynotes.component.BarButton
import com.raion.keynotes.component.NoteCard
import com.raion.keynotes.component.Notes
import com.raion.keynotes.component.RaionTextField
import com.raion.keynotes.component.UserDetail
import com.raion.keynotes.model.NoteItem
import com.raion.keynotes.navigation.NavEnum

//@Preview
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: RaionAPIViewModel,
    navController: NavController,
    addNote: (Pair<String, String>) -> Unit,
    deleteNote: (String) -> Unit
){
    // For testing
    Notes(viewModel = viewModel)
    UserDetail(viewModel = viewModel)

    var preventFlag = remember {
        mutableStateOf(false)
    }
    var newNoteTitle = remember {
        mutableStateOf("")
    }
    var newNoteDescription = remember {
        mutableStateOf("")
    }
    
    var noteRawList = viewModel.getNote.value.data
    var noteList: List<NoteItem>

    if (noteRawList != null) {
        noteList = noteRawList.data
    } else {
        noteList = listOf()
    }

    var userDetail = viewModel.getUserDetail.value.data

    var userId: String = ""
    var userName: String = ""
    var password: String = ""
    var salt: String = ""

    if(userDetail != null){
        userId   = userDetail.data.id
        userName = userDetail.data.name
        password = userDetail.data.password
        salt     = userDetail.data.salt
    }

    Surface(modifier = Modifier.fillMaxSize(), color = Color(30,30,30, 255)) {
        Scaffold(
            content = {
                Surface(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.15f), color = Color(30,30,30, 255)
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
                        Image(painter = painterResource(id = R.drawable.raion), contentDescription = "Raion", modifier = Modifier.padding(top = 12.dp, start = 5.dp))
                    }
                }
            },
            bottomBar = {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.883f),
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
                    colors = CardDefaults.cardColors(Color(30,30,30, 255)),
                    elevation = CardDefaults.cardElevation(10.dp)
                ) {
                    Scaffold(
                        content = {
                            Column(modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.92f)
                                .padding(15.dp)
                                .padding(top = 22.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Top
                            ) {
                                Text(text = "#RawrNotes", fontSize = 28.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.inverseSurface)
                                Spacer(modifier = Modifier.padding(5.dp))

                                if(noteRawList != null){
                                    LazyColumn(modifier = Modifier.fillMaxSize()){
                                        items(noteList){noteItem ->
                                            NoteCard(noteItem = noteItem){noteId ->
                                                if(noteId.isNotEmpty()){
                                                    //deleteNote(noteItem.noteId)
                                                    navController.navigate(route = NavEnum.NoteScreen.name+"/$noteId")
                                                }
                                            }
                                        }
                                    }

                                } else {

                                }
                            }
                        },
                        bottomBar = {
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .height(85.dp)
                                .padding(start = 15.dp, end = 15.dp)) {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(65.dp),
                                    shape = RoundedCornerShape(30.dp),
                                    elevation = CardDefaults.cardElevation(10.dp),
                                    colors = CardDefaults.cardColors(Color(51,47,51))
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalArrangement = Arrangement.SpaceEvenly,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(modifier = Modifier
                                            .width(85.dp)
                                            .height(75.dp)
                                            .padding(17.dp), contentAlignment = Alignment.Center){
                                            Icon(
                                                painter = painterResource(id = R.drawable.download),
                                                contentDescription = "home", tint = Color(255,199,0,255),
                                                modifier = Modifier.clickable { navController.navigate(route = NavEnum.DownloadScreen.name) }
                                            )
                                        }
                                        Card(
                                            modifier = Modifier
                                                .width(85.dp)
                                                .height(75.dp),
                                            shape = CircleShape,
                                            colors = CardDefaults.cardColors(Color(30,30,30, 255)),
                                            elevation = CardDefaults.cardElevation(5.dp),
                                            border = BorderStroke(3.dp, Color(255,199,0,255))

                                        ) {
                                            Box(modifier = Modifier
                                                .fillMaxSize()
                                                .padding(16.dp)
                                                .padding(top = 2.dp), contentAlignment = Alignment.Center){
                                                Icon(painter = painterResource(id = R.drawable.note), contentDescription = "home", tint = Color(255,199,0,255))
                                            }
                                        }
                                        Box(modifier = Modifier
                                            .width(85.dp)
                                            .height(75.dp)
                                            .padding(16.dp)
                                            .padding(top = 3.dp), contentAlignment = Alignment.Center){
                                            Icon(
                                                painter = painterResource(id = R.drawable.profile),
                                                contentDescription = "home", tint = Color(255,199,0,255),
                                                modifier = Modifier.clickable { navController.navigate(route = NavEnum.ProfileScreen.name) }
                                            )
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
                                    .clickable { navController.navigate(route = NavEnum.CreateNewNoteScreen.name) },
                                shape    = CircleShape,
                                colors   = CardDefaults.cardColors(Color(51,47,51)),
                                elevation = CardDefaults.cardElevation(10.dp),
                                border = BorderStroke(3.dp, color = Color(255,199,0,255))
                            ) {
                                Box(modifier = Modifier
                                    .fillMaxSize()
                                    .padding(15.dp)
                                    .padding(top = 2.dp), contentAlignment = Alignment.Center){
                                    Icon(painter = painterResource(id = R.drawable.add), contentDescription = "new pocket", tint = Color(255,199,0,255), modifier = Modifier.padding(2.dp
                                    ))
                                }
                            }
                        }
                    )
                }
            },
        )
    }
}