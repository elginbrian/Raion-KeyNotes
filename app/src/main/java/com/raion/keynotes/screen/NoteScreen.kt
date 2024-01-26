package com.raion.keynotes.screen

import android.annotation.SuppressLint
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Refresh
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.raion.keynotes.R
import com.raion.keynotes.model.NoteItem
import com.raion.keynotes.navigation.NavEnum


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    navController: NavController,
    noteId: String?,
    viewModel: RaionAPIViewModel
) {
    var noteRawList = viewModel.getNote.value.data
    var noteList: List<NoteItem>

    var noteTitle: String = ""
    var noteDescription: String = ""
    var noteCreatedAt: String = ""
    var noteUpdatedAt: String = ""

    if (noteRawList != null) {
        noteList = noteRawList.data

        for(i in noteList.indices){
            if(noteList[i].noteId.equals(noteId)){
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
        mutableStateOf("1")
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
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            tint = Color.White,
                            modifier = Modifier
                                .padding(bottom = 6.dp)
                                .clickable { navController.navigate(route = NavEnum.HomeScreen.name) },
                            contentDescription = ""
                        )
                        Spacer(modifier = Modifier.padding(5.dp))
                        Text(
                            text = noteTitle,
                            fontSize = 28.sp,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
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
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onPrimary),
                    elevation = CardDefaults.cardElevation(10.dp)
                ) {
                    Scaffold(
                        content = {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(15.dp)
                                    .padding(top = 22.dp),
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
                                                .height(70.dp),
                                            shape = RoundedCornerShape(20.dp),
                                            elevation = CardDefaults.cardElevation(8.dp),
                                            colors = CardDefaults.cardColors(Color(51,47,51))
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
                                                    fontSize = 15.sp,
                                                    fontWeight = FontWeight.SemiBold,
                                                    color = Color.White
                                                )
                                                Spacer(modifier = Modifier.padding(1.dp))
                                                Text(
                                                    text = "UpdatedAt: $noteUpdatedAt",
                                                    fontSize = 15.sp,
                                                    fontWeight = FontWeight.SemiBold,
                                                    color = Color.White
                                                )
                                            }
                                        }

                                        Card(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(430.dp)
                                                .padding(
                                                    //start = 25.dp,
                                                    //end = 25.dp,
                                                    top = 15.dp,
                                                    bottom = 15.dp
                                                ),
                                            shape = RoundedCornerShape(15.dp),
                                            elevation = CardDefaults.cardElevation(8.dp),
                                            //colors = CardDefaults.cardColors(MaterialTheme.colorScheme.inversePrimary)
                                        ) {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                            ) {
                                                Card(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .height(40.dp),
                                                    shape = RoundedCornerShape(
                                                        topStart = 15.dp,
                                                        topEnd = 15.dp
                                                    ),
                                                    colors = CardDefaults.cardColors(
                                                        Color(
                                                            51,47,51
                                                        )
                                                    )
                                                ) {
                                                    Row(
                                                        modifier = Modifier
                                                            .fillMaxSize()
                                                            .padding(start = 15.dp, end = 15.dp),
                                                        verticalAlignment = Alignment.CenterVertically,
                                                        horizontalArrangement = Arrangement.SpaceBetween
                                                    ) {
                                                        Text(
                                                            text = "Note",
                                                            fontSize = 18.sp,
                                                            fontWeight = FontWeight(500),
                                                            color = Color.White
                                                        )

                                                        Row(verticalAlignment = Alignment.CenterVertically) {

                                                            Spacer(modifier = Modifier.padding(3.dp))

                                                        }
                                                    }
                                                }

                                                Column(modifier = Modifier
                                                    .padding(8.dp)
                                                    .fillMaxSize(),
                                                    //verticalArrangement = Arrangement.SpaceBetween
                                                ) {
                                                    Text(text = noteDescription, fontSize = 15.sp, maxLines = 19, textAlign = TextAlign.Justify, lineHeight = 18.sp)
                                                }
                                            }
                                        }

                                        Card(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(55.dp),
                                            shape = RoundedCornerShape(20.dp),
                                            elevation = CardDefaults.cardElevation(8.dp),
                                            colors = CardDefaults.cardColors(Color(51,47,51))
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
                                                                //clickFlagReturn(clickFlag.value)
                                                            },
                                                        shape = CircleShape,
                                                        colors = CardDefaults.cardColors(Color(250, 110, 80)),
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
                                                                //clickFlagReturn(clickFlag.value)
                                                            },
                                                        shape = CircleShape,
                                                        colors = CardDefaults.cardColors(Color(185, 250, 80)),
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
                                                                //clickFlagReturn(clickFlag.value)
                                                            },
                                                        shape = CircleShape,
                                                        colors = CardDefaults.cardColors(Color(80, 120, 250)),
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
                                                                //clickFlagReturn(clickFlag.value)
                                                            },
                                                        shape = CircleShape,
                                                        colors = CardDefaults.cardColors(Color(170, 80, 250)),
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
                                                    )
                                            }
                                        }
                                        Box(modifier = Modifier
                                            .width(85.dp)
                                            .height(75.dp)
                                            .padding(16.dp)
                                            .padding(top = 3.dp)
                                            .clickable {

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

                        }
                    )
                }
            },
        )
    }
}