package com.raion.keynotes.component

import android.util.Log
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import com.raion.keynotes.model.getNoteResponse
import com.raion.keynotes.screen.RaionViewModel

@Composable
fun Notes(viewModel: RaionViewModel){
    val notes = viewModel.data.value.data?.toMutableList()
    if(viewModel.data.value.loading == true){
        CircularProgressIndicator()
        Log.d("Loading", "Note: Loading....")

    } else {
        notes?.forEach { noteItem ->
            Log.d("NoteItem", "Note: ${noteItem.message}")
        }
    }
    Log.d("SIZE", "Note: ${notes?.size}")
}