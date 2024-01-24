package com.raion.keynotes.screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raion.keynotes.data.DataExceptionHandling
import com.raion.keynotes.model.getNoteResponse
import com.raion.keynotes.repository.RaionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RaionViewModel @Inject constructor(private val repository: RaionRepository): ViewModel(){
    val data: MutableState<DataExceptionHandling<ArrayList<getNoteResponse>, Boolean, Exception>> =
        mutableStateOf(
            DataExceptionHandling(null, true, Exception(""))
        )

        init {
            getNote()
        }

        private fun getNote(){
            viewModelScope.launch {
                data.value.loading = true
                data.value = repository.getNoteResponse()

                if(data.value.data.toString().isNotEmpty()){
                    data.value.loading = false
                }
            }
        }
}