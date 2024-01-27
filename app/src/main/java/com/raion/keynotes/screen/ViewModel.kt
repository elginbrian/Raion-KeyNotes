package com.raion.keynotes.screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raion.keynotes.data.DataExceptionHandling
import com.raion.keynotes.data.NoteDAO
import com.raion.keynotes.model.NoteClass
import com.raion.keynotes.model.GetNoteResponse
import com.raion.keynotes.model.GetUserDetailResponse
import com.raion.keynotes.model.PostLoginResponse
import com.raion.keynotes.repository.NoteDAORepository
import com.raion.keynotes.repository.RaionAPIRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RaionAPIViewModel @Inject constructor(private val repository: RaionAPIRepository): ViewModel(){
    val getNote: MutableState<DataExceptionHandling<GetNoteResponse, Boolean, Exception>> =
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


    val getUserDetail: MutableState<DataExceptionHandling<GetUserDetailResponse, Boolean, Exception>> =
        mutableStateOf(
            DataExceptionHandling(null, true, Exception(""))
        )
    init {
        getUserDetail()
    }
    private fun getUserDetail(){
        viewModelScope.launch {
            getUserDetail.value.loading = true
            getUserDetail.value = repository.getUserDetailResponse()

            if(getUserDetail.value.data.toString().isNotEmpty()){
                getUserDetail.value.loading = false
            }
        }
    }

    val postLoginResponse = repository.postLoginExceptionHandling.data

    fun postNote(title: String, description: String)                                   = viewModelScope.launch { repository.postNoteRequest(title, description) }
    fun postRegister(nim: String, name: String, password: String, description: String) = viewModelScope.launch { repository.registerRequest(nim, name, password, description) }
    fun postLogin(nim: String, password: String)                                       = viewModelScope.launch { repository.loginRequest(nim, password) }
    fun putNote(noteId: String, title: String, description: String)                    = viewModelScope.launch { repository.putNoteRequest(noteId, title, description) }
    fun deleteNote(noteId: String)                                                     = viewModelScope.launch { repository.deleteNoteRequest(noteId) }
}

@HiltViewModel
class NoteDAOViewModel @Inject constructor(val NoteDAORepository: NoteDAORepository, val NoteDAO: NoteDAO): ViewModel(){
    val _noteList = MutableStateFlow<List<NoteClass>>(emptyList())
    val notelist  = _noteList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO){
            NoteDAORepository.getAllNote().distinctUntilChanged().collect{
                listOfNote -> if(listOfNote.isNullOrEmpty()){

                } else {
                    _noteList.value = listOfNote
                }
            }
        }
    }

    fun addNote(NoteClass: NoteClass) = viewModelScope.launch { NoteDAORepository.addNote(NoteClass) }
    fun removeNote(noteId: String) = viewModelScope.launch { NoteDAORepository.deleteNote(noteId) }
    fun updateNote(noteId: String, description: String) = viewModelScope.launch { NoteDAORepository.updateNote(noteId, description) }
    fun deleteAllNote(NoteClass: NoteClass) = viewModelScope.launch { NoteDAORepository.deleteAllNote() }
}