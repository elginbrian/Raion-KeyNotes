package com.raion.keynotes.screen

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.raion.keynotes.R
import com.raion.keynotes.component.TransparentTextField
import com.raion.keynotes.model.NoteItem
import com.raion.keynotes.navigation.NavEnum


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    navController: NavController,
    noteId: String?,
    viewModel: RaionAPIViewModel,
    deleteNote: (String) -> Unit,
    downloadNote: (List<String>) -> Unit,
    putNote: (List<String>) -> Unit
) {
    val context = LocalContext.current as Activity
    var noteRawList = viewModel.getNote.value.data
    var noteList: List<NoteItem>

    var thisNoteId: String = ""
    var noteTitle: String = ""
    var noteDescription: String = ""
    var noteCreatedAt: String = ""
    var noteUpdatedAt: String = ""

    if (noteRawList != null) {
        noteList = noteRawList.data

        for(i in noteList.indices){
            if(noteList[i].noteId.equals(noteId)){
                thisNoteId      = noteList[i].noteId
                noteTitle       = noteList[i].title
                noteDescription = noteList[i].description
                noteCreatedAt   = noteList[i].createdAt
                noteUpdatedAt   = noteList[i].updatedAt
            }
        }
    } else {
        noteList = emptyList()
    }

    var colorFlag = remember {
        mutableStateOf("0")
    }
    var navBarFlag = remember {
        mutableStateOf("0")
    }
    var newNoteTitle = remember {
        mutableStateOf(noteTitle)
    }
    var newNoteDescription = remember {
        mutableStateOf(noteDescription)
    }
    var noteColor: Color = Color.White
    if(newNoteDescription.value.contains("#NoteColorRed")){
        noteColor = Color(250, 175, 175)
    } else if(newNoteDescription.value.contains("#NoteColorGreen")){
        noteColor = Color(220, 250, 175)
    } else if (newNoteDescription.value.contains("#NoteColorBlue")){
        noteColor = Color(175, 210, 250)
    } else if (newNoteDescription.value.contains("#NoteColorViolet")){
        noteColor = Color(210, 175, 250)
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            content = {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.15f),
                    color = Color(30,30,30, 255)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(15.dp)
                            .padding(bottom = 30.dp, end = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(0.9f),
                            horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                tint = Color.White,
                                modifier = Modifier
                                    .clickable {
                                        putNote(
                                            listOf(
                                                thisNoteId,
                                                newNoteTitle.value,
                                                newNoteDescription.value
                                            )
                                        )
                                        navController.navigate(route = NavEnum.HomeScreen.name)
                                        context.recreate()
                                        navController.navigate(route = NavEnum.HomeScreen.name)
                                    },
                                contentDescription = ""
                            )
                            Spacer(modifier = Modifier.padding(5.dp))
                            TransparentTextField(
                                text = newNoteTitle.value,
                                onValueChange = {
                                    if ((it.all { char -> char.isDefined() || char.isWhitespace() })) {
                                        newNoteTitle.value = it
                                    }
                                },
                                onFocusChange = {},
                                singleLine = false,
                                textStyle = TextStyle(color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.SemiBold),
                                modifier = Modifier.fillMaxHeight()
                            )
                        }

                        Box(modifier = Modifier
                            .width(20.dp)
                            .height(20.dp)){
                            Icon(
                                painter = painterResource(id = R.drawable.disk),
                                tint = Color.White,
                                modifier = Modifier
                                    .clickable {
                                        putNote(
                                            listOf(
                                                thisNoteId,
                                                newNoteTitle.value,
                                                newNoteDescription.value
                                            )
                                        )
                                    },
                                contentDescription = ""
                            )
                        }
                    }
                }
            },
            bottomBar = {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.883f),
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
                    colors = CardDefaults.cardColors(noteColor),
                    elevation = CardDefaults.cardElevation(10.dp)
                ) {
                    Scaffold(
                        containerColor = noteColor,
                        contentColor = noteColor,
                        content = {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(15.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Top
                            ) {
                                Column(
                                    modifier = Modifier
                                    //.fillMaxSize()
                                ) {
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(60.dp),
                                        shape = RoundedCornerShape(20.dp),
                                        elevation = CardDefaults.cardElevation(8.dp),
                                        colors = CardDefaults.cardColors(Color(51, 47, 51))
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(start = 16.dp),
                                            horizontalAlignment = Alignment.Start,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Text(
                                                text = "CreatedAt: $noteCreatedAt",
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight.Normal,
                                                color = Color.White
                                            )
                                            Spacer(modifier = Modifier.padding(1.dp))
                                            Text(
                                                text = "UpdatedAt: $noteUpdatedAt",
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight.Normal,
                                                color = Color.White
                                            )
                                        }
                                    }
                                    Column(modifier = Modifier
                                        .fillMaxSize()
                                        .padding(10.dp)
                                        .verticalScroll(rememberScrollState())
                                    ) {
                                        TransparentTextField(
                                            text = newNoteDescription.value,
                                            onValueChange = {
                                                if ((it.all { char -> char.isDefined() || char.isWhitespace() })) {
                                                    newNoteDescription.value = it
                                                }
                                            },
                                            onFocusChange = {},
                                            singleLine = false,
                                            textStyle = TextStyle(color = Color(30,30,30, 255), fontSize = 16.sp),
                                            modifier = Modifier.fillMaxHeight()
                                        )
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
                                            .padding(17.dp)
                                            .clickable {
                                                if (navBarFlag.value == "1") {
                                                    navBarFlag.value = "0"
                                                } else {
                                                    navBarFlag.value = "1"
                                                }
                                            }, contentAlignment = Alignment.Center) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.color),
                                                contentDescription = "home",
                                                tint = Color.White
                                            )
                                        }
                                        Card(
                                            modifier = Modifier
                                                .width(85.dp)
                                                .height(75.dp)
                                                .clickable {
                                                    if (navBarFlag.value == "2") {
                                                        navBarFlag.value = "0"
                                                    } else {
                                                        navBarFlag.value = "2"
                                                    }
                                                },
                                            shape = CircleShape,
                                            colors = CardDefaults.cardColors(Color(255,199,0,255)),
                                            elevation = CardDefaults.cardElevation(5.dp),
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(
                                                        15.dp
                                                    ),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.download2),
                                                        contentDescription = "home",
                                                        tint = Color(30,30,30, 255)
                                                    )
                                            }
                                        }
                                        Box(modifier = Modifier
                                            .width(85.dp)
                                            .height(75.dp)
                                            .padding(16.dp)
                                            .padding(top = 3.dp)
                                            .clickable {
                                                if (navBarFlag.value == "3") {
                                                    navBarFlag.value = "0"
                                                } else {
                                                    navBarFlag.value = "3"
                                                }
                                            }, contentAlignment = Alignment.Center) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.delete),
                                                contentDescription = "home",
                                                tint = Color.White
                                            )
                                        }
                                    }
                                }
                            }
                        },
                        floatingActionButton = {
                            when (navBarFlag.value) {
                                "1" -> {
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth(0.92f)
                                            .height(55.dp),
                                        shape = RoundedCornerShape(20.dp),
                                        elevation = CardDefaults.cardElevation(8.dp),
                                        colors = CardDefaults.cardColors(Color(51, 47, 51))
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(start = 16.dp, end = 16.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "Note Color: ",
                                                fontSize = 15.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                color = Color.White
                                            )

                                            Row(
                                                horizontalArrangement = Arrangement.Start,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Card(
                                                    modifier = Modifier
                                                        .width(35.dp)
                                                        .height(35.dp)
                                                        .clickable {
                                                            colorFlag.value = "1"
                                                            newNoteDescription.value =
                                                                newNoteDescription.value.replace(
                                                                    "#NoteColorRed",
                                                                    "\b\b"
                                                                )
                                                            newNoteDescription.value =
                                                                newNoteDescription.value.replace(
                                                                    "#NoteColorGreen",
                                                                    "\b\b"
                                                                )
                                                            newNoteDescription.value =
                                                                newNoteDescription.value.replace(
                                                                    "#NoteColorBlue",
                                                                    "\b\b"
                                                                )
                                                            newNoteDescription.value =
                                                                newNoteDescription.value.replace(
                                                                    "#NoteColorViolet",
                                                                    "\b\b"
                                                                )
                                                            newNoteDescription.value =
                                                                newNoteDescription.value + "\n#NoteColorRed"
                                                        },
                                                    shape = CircleShape,
                                                    colors = CardDefaults.cardColors(
                                                        Color(
                                                            250,
                                                            110,
                                                            80
                                                        )
                                                    ),
                                                    border =
                                                    if (colorFlag.value == "1") {
                                                        BorderStroke(4.dp, Color.White)
                                                    } else {
                                                        null
                                                    }
                                                ) {}

                                                Spacer(modifier = Modifier.padding(5.dp))

                                                Card(
                                                    modifier = Modifier
                                                        .width(35.dp)
                                                        .height(35.dp)
                                                        .clickable {
                                                            colorFlag.value = "2"
                                                            newNoteDescription.value =
                                                                newNoteDescription.value.replace(
                                                                    "#NoteColorRed",
                                                                    "\b\b"
                                                                )
                                                            newNoteDescription.value =
                                                                newNoteDescription.value.replace(
                                                                    "#NoteColorGreen",
                                                                    "\b\b"
                                                                )
                                                            newNoteDescription.value =
                                                                newNoteDescription.value.replace(
                                                                    "#NoteColorBlue",
                                                                    "\b\b"
                                                                )
                                                            newNoteDescription.value =
                                                                newNoteDescription.value.replace(
                                                                    "#NoteColorViolet",
                                                                    "\b\b"
                                                                )
                                                            newNoteDescription.value =
                                                                newNoteDescription.value + "\n#NoteColorGreen"
                                                        },
                                                    shape = CircleShape,
                                                    colors = CardDefaults.cardColors(
                                                        Color(
                                                            185,
                                                            250,
                                                            80
                                                        )
                                                    ),
                                                    border =
                                                    if (colorFlag.value == "2") {
                                                        BorderStroke(4.dp, Color.White)
                                                    } else {
                                                        null
                                                    }
                                                ) {}

                                                Spacer(modifier = Modifier.padding(5.dp))

                                                Card(
                                                    modifier = Modifier
                                                        .width(35.dp)
                                                        .height(35.dp)
                                                        .clickable {
                                                            colorFlag.value = "3"
                                                            newNoteDescription.value =
                                                                newNoteDescription.value.replace(
                                                                    "#NoteColorRed",
                                                                    "\b\b"
                                                                )
                                                            newNoteDescription.value =
                                                                newNoteDescription.value.replace(
                                                                    "#NoteColorGreen",
                                                                    "\b\b"
                                                                )
                                                            newNoteDescription.value =
                                                                newNoteDescription.value.replace(
                                                                    "#NoteColorBlue",
                                                                    "\b\b"
                                                                )
                                                            newNoteDescription.value =
                                                                newNoteDescription.value.replace(
                                                                    "#NoteColorViolet",
                                                                    "\b\b"
                                                                )
                                                            newNoteDescription.value =
                                                                newNoteDescription.value + "\n#NoteColorBlue"
                                                        },
                                                    shape = CircleShape,
                                                    colors = CardDefaults.cardColors(
                                                        Color(
                                                            80,
                                                            120,
                                                            250
                                                        )
                                                    ),
                                                    border =
                                                    if (colorFlag.value == "3") {
                                                        BorderStroke(4.dp, Color.White)
                                                    } else {
                                                        null
                                                    }
                                                ) {}

                                                Spacer(modifier = Modifier.padding(5.dp))

                                                Card(
                                                    modifier = Modifier
                                                        .width(35.dp)
                                                        .height(35.dp)
                                                        .clickable {
                                                            colorFlag.value = "4"
                                                            newNoteDescription.value =
                                                                newNoteDescription.value.replace(
                                                                    "#NoteColorRed",
                                                                    "\b\b"
                                                                )
                                                            newNoteDescription.value =
                                                                newNoteDescription.value.replace(
                                                                    "#NoteColorGreen",
                                                                    "\b\b"
                                                                )
                                                            newNoteDescription.value =
                                                                newNoteDescription.value.replace(
                                                                    "#NoteColorBlue",
                                                                    "\b\b"
                                                                )
                                                            newNoteDescription.value =
                                                                newNoteDescription.value.replace(
                                                                    "#NoteColorViolet",
                                                                    "\b\b"
                                                                )
                                                            newNoteDescription.value =
                                                                newNoteDescription.value + "\n#NoteColorViolet"
                                                        },
                                                    shape = CircleShape,
                                                    colors = CardDefaults.cardColors(
                                                        Color(
                                                            170,
                                                            80,
                                                            250
                                                        )
                                                    ),
                                                    border =
                                                    if (colorFlag.value == "4") {
                                                        BorderStroke(4.dp, Color.White)
                                                    } else {
                                                        null
                                                    }
                                                ) {}
                                            }
                                        }
                                    }
                                }

                                "2" -> {
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth(0.92f)
                                            .height(55.dp)
                                            .clickable {
                                                downloadNote(
                                                    listOf(
                                                        thisNoteId,
                                                        noteTitle,
                                                        noteDescription,
                                                        noteCreatedAt,
                                                        noteUpdatedAt
                                                    )
                                                )
                                                context.recreate()
                                            },
                                        shape = RoundedCornerShape(20.dp),
                                        elevation = CardDefaults.cardElevation(8.dp),
                                        colors = CardDefaults.cardColors(Color(80, 120, 250))
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(start = 16.dp, end = 16.dp),
                                            horizontalArrangement = Arrangement.Center,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "Download this note",
                                                fontSize = 15.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                color = Color.White
                                            )
                                        }
                                    }
                                }

                                "3" -> {
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth(0.92f)
                                            .height(55.dp)
                                            .clickable {
                                                deleteNote(thisNoteId)
                                                navController.navigate(route = NavEnum.HomeScreen.name)
                                                context.recreate()
                                                navController.navigate(route = NavEnum.HomeScreen.name)
                                            },
                                        shape = RoundedCornerShape(20.dp),
                                        elevation = CardDefaults.cardElevation(8.dp),
                                        colors = CardDefaults.cardColors(Color(250, 110, 80))
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(start = 16.dp, end = 16.dp),
                                            horizontalArrangement = Arrangement.Center,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "Delete this note",
                                                fontSize = 15.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                color = Color.White
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    )
                }
            },
        )
    }
}