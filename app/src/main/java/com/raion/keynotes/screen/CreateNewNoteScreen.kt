package com.raion.keynotes.screen

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Toast
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
import com.raion.keynotes.component.NoteColorBar
import com.raion.keynotes.component.TransparentTextField
import com.raion.keynotes.model.NoteItem
import com.raion.keynotes.navigation.NavEnum

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNewNoteScreen(
    navController: NavController,
    viewModel: RaionAPIViewModel,
    postNote: (Pair<String, String>) -> Unit
) {
    val context = LocalContext.current as Activity
    var colorFlag = remember {
        mutableStateOf("0")
    }
    var navBarFlag = remember {
        mutableStateOf("0")
    }
    var newNoteTitle = remember {
        mutableStateOf("Insert Title Here")
    }
    var newNoteDescription = remember {
        mutableStateOf("Insert Description Here!\n(Tap the arrow back button to save)")
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
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            tint = Color.White,
                            modifier = Modifier
                                .clickable {
                                    postNote(Pair(newNoteTitle.value, newNoteDescription.value))
                                    navController.navigate(route = NavEnum.HomeScreen.name)
                                    context.recreate()
                                    Toast.makeText(context, "${newNoteTitle.value} saved", Toast.LENGTH_LONG).show()
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
                            onFocusChange = {

                            },
                            singleLine = false,
                            textStyle = TextStyle(color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.SemiBold),
                            modifier = Modifier.fillMaxHeight()
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

                                ) {
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

                        floatingActionButton = {
                            NoteColorBar(
                                colorFlag = colorFlag.value,
                                newNoteDescription = newNoteDescription.value,
                                returnColorFlag = { colorFlag.value = it },
                                returnNoteDescription = { newNoteDescription.value = it }
                            )
                        }
                    )
                }
            },
        )
    }
}