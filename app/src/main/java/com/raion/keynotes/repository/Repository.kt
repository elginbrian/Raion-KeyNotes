package com.raion.keynotes.repository

import android.util.Log
import com.raion.keynotes.data.DataExceptionHandling
import com.raion.keynotes.data.NoteDAO
import com.raion.keynotes.data.TokenDAO
import com.raion.keynotes.model.NoteClass
import com.raion.keynotes.model.PostNoteRequest
import com.raion.keynotes.model.GetNoteResponse
import com.raion.keynotes.model.GetUserDetailResponse
import com.raion.keynotes.model.PostLoginRequest
import com.raion.keynotes.model.PostRegisterRequest
import com.raion.keynotes.model.PutNoteRequest
import com.raion.keynotes.model.PutUserDetailRequest
import com.raion.keynotes.model.TokenClass
import com.raion.keynotes.network.RaionAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class RaionAPIRepository @Inject constructor(private val api: RaionAPI, private val tokenDAO: TokenDAO){
    private val getNoteException       = DataExceptionHandling<GetNoteResponse, Boolean, Exception>()
    private val getUserDetailException = DataExceptionHandling<GetUserDetailResponse, Boolean, Exception>()

    suspend fun retrieveToken(): String {
        val tokenClass = tokenDAO.getToken().firstOrNull()
        return "Bearer " + tokenClass?.tokenString
    }

    suspend fun getNoteResponse(): DataExceptionHandling<GetNoteResponse, Boolean, Exception>{
        try {
            getNoteException.loading = true
            getNoteException.data    = api.getNote(token = retrieveToken())

            if(getNoteException.data.toString().isNotEmpty()) {
                getNoteException.loading = false
            }
        } catch (exception: Exception){
            getNoteException.e = exception
            Log.d("Repo exception", "getNoteResponse: ${getNoteException.e!!.localizedMessage}")
            Log.d("Repo exception", "getNoteResponse: ${retrieveToken()}")
        }
        return getNoteException
    }

    suspend fun getUserDetailResponse(): DataExceptionHandling<GetUserDetailResponse, Boolean, Exception>{
        try {
            getUserDetailException.loading = true
            getUserDetailException.data    = api.getUserDetail(token = retrieveToken())
            if(getUserDetailException.data.toString().isNotEmpty()) {
                getUserDetailException.loading = false
            }
        } catch (exception: Exception){
            getUserDetailException.e = exception
            Log.d("Repo exception", "getUserDetailResponse: ${getUserDetailException.e!!.localizedMessage}")
            Log.d("Repo exception", "getUserDetailResponse: ${retrieveToken()}")
        }
        return getUserDetailException
    }

    suspend fun postNoteRequest(title: String, description: String){
        val noteRequest = PostNoteRequest(title, description)
        try{
            val response = api.postNote(request = noteRequest, token = retrieveToken())
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
            val response = api.postRegister(request = registerRequest, token = retrieveToken())
            if(!response.error){
                Log.d("Repo sucsess", "Response: ${response.data}")
            } else {
                Log.d("Repo exception", "Error: ${response.status} | ${response.message}")
            }
        } catch (e: Exception){
            Log.d("Repo exception", "${e.printStackTrace()}")
        }
    }

    suspend fun loginRequest(nim: String, password: String){
        val loginRequest = PostLoginRequest(nim, password)
        val response = api.postLogin(request = loginRequest, token = retrieveToken())
        try {
            if(!response.error){
                Log.d("Repo sucsess", "Response: ${response.data.token}")
                tokenDAO.insert(TokenClass(tokenId = response.data.token, tokenString = response.data.token))
            } else {
                Log.d("Repo exception", "Error: ${response.status} | ${response.message}")
            }
        } catch (e: Exception){
            Log.d("Repo exception", "${e.printStackTrace()}")
        }
    }

    suspend fun putNoteRequest(noteId: String, title: String, description: String){
        val putNoteRequest = PutNoteRequest(title, description)
        try {
            val response = api.putNote(noteId = noteId, request = putNoteRequest, token = retrieveToken())
            if(!response.error){
                Log.d("Repo sucsess", "Response: ${response.data}")
            } else {
                Log.d("Repo exception", "Error: ${response.status} | ${response.message}")
            }
        } catch (e: Exception){
            Log.d("Repo exception", "${e.printStackTrace()}")
        }
    }

    suspend fun putUserDetailRequest(name: String, password: String, description: String){
        val putUserDetailRequest = PutUserDetailRequest(name, password, description)
        try {
            val response = api.putUserDetail(request = putUserDetailRequest, token = retrieveToken())
            if(!response.error){
                Log.d("Repo sucsess", "Response: ${response.data}")
            } else {
                Log.d("Repo exception", "Error: ${response.status} | ${response.message}")
            }
        } catch (e: Exception){
            Log.d("Repo exception", "${e.printStackTrace()}")
        }
    }

    suspend fun deleteNoteRequest(noteId: String){
        try {
            val response = api.deleteNote(noteId = noteId, token = retrieveToken())
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