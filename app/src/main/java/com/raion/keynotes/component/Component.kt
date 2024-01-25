package com.raion.keynotes.component

import android.util.Log
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
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
