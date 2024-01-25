package com.raion.keynotes.component

import android.util.Log
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import com.raion.keynotes.screen.RaionViewModel

@Composable
fun Notes(viewModel: RaionViewModel){
    val notes = viewModel.getNote.value.data

    if(viewModel.getNote.value.loading == true){
        CircularProgressIndicator()
        Log.d("Loading", "Note: Loading....")

    } else {
        Log.d("Notes at Component.kt Log.d", "Note: Message -> ${notes?.message} | Count: -> ${notes?.count}")
        Log.d("Notes at Component.kt Log.d", "Note: Data -> ${notes?.data.toString()}")

    }
}
