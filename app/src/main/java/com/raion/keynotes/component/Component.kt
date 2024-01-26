package com.raion.keynotes.component

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raion.keynotes.model.NoteItem
import com.raion.keynotes.screen.RaionAPIViewModel

@Composable
fun Notes(
    viewModel: RaionAPIViewModel,
){
    val notes = viewModel.getNote.value.data
    var noteID: String = ""

    if(viewModel.getNote.value.loading == true){
        CircularProgressIndicator()
        Log.d("Loading", "Note: Loading....")

    } else {
        Log.d("Notes at Component.kt Log.d", "Note: Message -> ${notes?.message} | Count: -> ${notes?.count}")
        Log.d("Notes at Component.kt Log.d", "Note: Data -> ${notes?.data.toString()}")
    }
}

@Composable
fun UserDetail(
    viewModel: RaionAPIViewModel
){
    val userDetail = viewModel.getUserDetail.value.data
    var userId: String = ""
    var userName: String = ""
    var password: String = ""
    var salt: String = ""

    if(viewModel.getNote.value.loading == true){
        CircularProgressIndicator()
        Log.d("Loading", "UserDetail: Loading....")

    } else {
        Log.d("UserDetail at Component.kt Log.d", "User Detail: Message -> ${userDetail?.message}")
        Log.d("UserDetail at Component.kt Log.d", "User Detail: Data -> ${userDetail?.data.toString()}")
    }
}

@Composable
fun NoteCard(
    noteItem: NoteItem
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(
                start = 10.dp,
                end = 10.dp,
                top = 15.dp,
                bottom = 5.dp
            ),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        //colors = CardDefaults.cardColors(MaterialTheme.colorScheme.inversePrimary)
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
                colors = CardDefaults.cardColors(Color(30,30,30, 255))
            ) {
                Row(modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 15.dp, end = 15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = noteItem.title, fontSize = 18.sp ,fontWeight = FontWeight(500), color = Color.White)

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = noteItem.updatedAt, fontSize = 12.sp, color = Color.White)
                    }
                }
            }

            Column(modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
                //verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = noteItem.description, fontSize = 12.sp, maxLines = 8, textAlign = TextAlign.Justify, lineHeight = 13.sp)
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RaionTextField(
    modifier: Modifier = Modifier.fillMaxWidth(),
    text: String,
    label: String,
    maxLine: Int = 1,
    onTextChange: (String) -> Unit,
    onImeAction: () -> Unit = {},
    keyboardType: KeyboardType = KeyboardType.Text
){
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = text,
        onValueChange = onTextChange,
        colors = TextFieldDefaults.textFieldColors(textColor = MaterialTheme.colorScheme.primary),
        maxLines = maxLine,
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = {
            onImeAction
            keyboardController?.hide()
        }),

        modifier = Modifier
    )
}

@Composable
fun BarButton(
    text: String = "Lorem Ipsum",
    color: Color = MaterialTheme.colorScheme.primary,
    whenWidgetClicked: (String) -> Unit = {},
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable { whenWidgetClicked("trigger") },
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(color),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = text, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
        }
    }
}
