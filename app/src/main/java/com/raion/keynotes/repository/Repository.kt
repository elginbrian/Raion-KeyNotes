package com.raion.keynotes.repository

import android.util.Log
import com.raion.keynotes.data.DataExceptionHandling
import com.raion.keynotes.data.NoteDAO
import com.raion.keynotes.model.NoteClass
import com.raion.keynotes.model.PostNoteRequest
import com.raion.keynotes.model.GetNoteResponse
import com.raion.keynotes.model.GetUserDetailResponse
import com.raion.keynotes.model.PostLoginRequest
import com.raion.keynotes.model.PostLoginResponse
import com.raion.keynotes.model.PostRegisterRequest
import com.raion.keynotes.network.RaionAPI
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RaionAPIRepository @Inject constructor(private val api: RaionAPI){
    private val getNoteExceptionHandling       = DataExceptionHandling<GetNoteResponse, Boolean, Exception>()
    private val getUserDetailExceptionHandling = DataExceptionHandling<GetUserDetailResponse, Boolean, Exception>()
    val postLoginExceptionHandling     = DataExceptionHandling<PostLoginResponse, Boolean, Exception>()

    suspend fun getNoteResponse(): DataExceptionHandling<GetNoteResponse, Boolean, Exception>{
        try {
            getNoteExceptionHandling.loading = true
            getNoteExceptionHandling.data    = api.getNote()

            if(getNoteExceptionHandling.data.toString().isNotEmpty()) {
                getNoteExceptionHandling.loading = false
            }

        } catch (exception: Exception){
            getNoteExceptionHandling.e = exception
            Log.d("Repo exception", "getNoteResponse: ${getNoteExceptionHandling.e!!.localizedMessage}")
        }
        return getNoteExceptionHandling
    }

    suspend fun getUserDetailResponse(): DataExceptionHandling<GetUserDetailResponse, Boolean, Exception>{
        try {
            getUserDetailExceptionHandling.loading = true
            getUserDetailExceptionHandling.data    = api.getUserDetail()

            if(getUserDetailExceptionHandling.data.toString().isNotEmpty()) {
                getUserDetailExceptionHandling.loading = false
            }

        } catch (exception: Exception){
            getUserDetailExceptionHandling.e = exception
            Log.d("Repo exception", "getUserDetailResponse: ${getUserDetailExceptionHandling.e!!.localizedMessage}")
        }
        return getUserDetailExceptionHandling
    }

    suspend fun postNoteRequest(title: String, description: String){
        val noteRequest = PostNoteRequest(title, description)
        try{
            val response = api.postNote(noteRequest)

            if(!response.error){
                Log.d("Repo sucsess", "Response: ${response.data}")
            } else {
                Log.d("Repo exception", "Error: ${response.status} | ${response.message}")
            }
        } catch (e: Exception){
            Log.d("Repo exception", "${e.printStackTrace()}")
        }
    }

    suspend fun registerRequest(nim: String, name: String, password: String, description: String){
        val registerRequest = PostRegisterRequest(nim, name, password, description)
        try {
            val response = api.postRegister(registerRequest)

            if(!response.error){
                Log.d("Repo sucsess", "Response: ${response.data}")
            } else {
                Log.d("Repo exception", "Error: ${response.status} | ${response.message}")
            }
        } catch (e: Exception){
            Log.d("Repo exception", "${e.printStackTrace()}")
        }
    }

    suspend fun loginRequest(nim: String, password: String): DataExceptionHandling<PostLoginResponse, Boolean, Exception>{
        val loginRequest = PostLoginRequest(nim, password)
        try {
            postLoginExceptionHandling.data    = api.postLogin(loginRequest)
            postLoginExceptionHandling.loading = true

            if(postLoginExceptionHandling.data.toString().isNotEmpty()) {
                postLoginExceptionHandling.loading = false
            }
        } catch (e: Exception){
            postLoginExceptionHandling.e = e
            Log.d("Repo exception", "postLoginResponse: ${postLoginExceptionHandling.e!!.localizedMessage}")
        }
        return postLoginExceptionHandling
    }

    suspend fun deleteNoteRequest(noteId: String){
        try {
            val response = api.deleteNote(noteId)

            if(!response.error){
                Log.d("Repo sucsess", "Response: ${response.data}")
            } else {
                Log.d("Repo exception", "Error: ${response.status} | ${response.message}")
            }
        } catch (e: Exception){
            Log.d("Repo exception", "${e.printStackTrace()}")
        }
    }
}



class NoteDAORepository @Inject constructor(private val NoteDAO: NoteDAO){
    suspend fun addNote(NoteClass: NoteClass) = NoteDAO.insert(NoteClass)

    suspend fun updateNote(noteId: String, description: String){
        var existingNote = NoteDAO.getNoteId(noteId)
        existingNote.description = description

        NoteDAO.update(existingNote)
    }

    suspend fun deleteNote(noteId: String){
        var existingNote = NoteDAO.getNoteId(noteId)
        NoteDAO.deleteNote(existingNote)
    }

    suspend fun deleteAllNote() = NoteDAO.deleteAll()

    fun getAllNote(): Flow<List<NoteClass>> = NoteDAO.getNote()
}