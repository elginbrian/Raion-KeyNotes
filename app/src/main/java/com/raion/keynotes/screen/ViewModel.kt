package com.raion.keynotes.screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raion.keynotes.data.DataExceptionHandling
import com.raion.keynotes.model.getNoteResponse
import com.raion.keynotes.model.postNoteResponse
import com.raion.keynotes.repository.RaionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RaionViewModel @Inject constructor(private val repository: RaionRepository): ViewModel(){
    val getNote: MutableState<DataExceptionHandling<getNoteResponse, Boolean, Exception>> =
        mutableStateOf(
            DataExceptionHandling(null, true, Exception(""))
        )

        init {
            getNote()
        }

        private fun getNote(){
            viewModelScope.launch {
                getNote.value.loading = true
                getNote.value = repository.getNoteResponse()

                if(getNote.value.data.toString().isNotEmpty()){
                    getNote.value.loading = false
                }
            }
        }

    val postNote: MutableState<DataExceptionHandling<postNoteResponse, Boolean, Exception>> =
        mutableStateOf(
            DataExceptionHandling(null, true, Exception(""))
        )

        init {
            postNote()
        }

        private fun postNote(){
            viewModelScope.launch {
                postNote.value.loading = true
                postNote.value = repository.postNoteResponse()

                if(getNote.value.data.toString().isNotEmpty()){
                    postNote.value.loading = false
                }
            }
        }
}