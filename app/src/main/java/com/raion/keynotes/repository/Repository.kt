package com.raion.keynotes.repository

import android.util.Log
import com.raion.keynotes.data.DataExceptionHandling
import com.raion.keynotes.data.NoteDAO
import com.raion.keynotes.model.DeleteNoteResponse
import com.raion.keynotes.model.NoteClass
import com.raion.keynotes.model.PostNoteRequest
import com.raion.keynotes.model.GetNoteResponse
import com.raion.keynotes.model.GetUserDetailResponse
import com.raion.keynotes.model.PostNoteResponse
import com.raion.keynotes.network.RaionAPI
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RaionAPIRepository @Inject constructor(private val api: RaionAPI){
    private val getNoteExceptionHandling       = DataExceptionHandling<GetNoteResponse, Boolean, Exception>()
    private val getUserDetailExceptionHandling = DataExceptionHandling<GetUserDetailResponse, Boolean, Exception>()

    private val postNoteExceptionHandling      = DataExceptionHandling<PostNoteResponse, Boolean, Exception>()

    private val deleteNoteExceptionHandling    = DataExceptionHandling<DeleteNoteResponse, Boolean, Exception>()

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

    suspend fun postNoteRequest(title: String, description: String): DataExceptionHandling<PostNoteResponse, Boolean, Exception>{
        //val noteRequest = postNoteRequest(title, description)
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
        return postNoteExceptionHandling
    }

    suspend fun deleteNoteRequest(noteId: String): DataExceptionHandling<DeleteNoteResponse, Boolean, Exception>{
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
        return deleteNoteExceptionHandling
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