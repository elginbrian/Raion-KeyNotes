package com.raion.keynotes.repository

import android.util.Log
import com.raion.keynotes.data.DataExceptionHandling
import com.raion.keynotes.data.NoteDAO
import com.raion.keynotes.model.NoteClass
import com.raion.keynotes.model.getNoteResponse
import com.raion.keynotes.model.getUserDetailResponse
import com.raion.keynotes.model.postNoteResponse
import com.raion.keynotes.network.RaionAPI
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RaionAPIRepository @Inject constructor(private val api: RaionAPI){
    private val getNoteExceptionHandling       = DataExceptionHandling<getNoteResponse, Boolean, Exception>()
    private val getUserDetailExceptionHandling = DataExceptionHandling<getUserDetailResponse, Boolean, Exception>()

    private val postNoteExceptionHandling      = DataExceptionHandling<postNoteResponse, Boolean, Exception>()

    suspend fun getNoteResponse(): DataExceptionHandling<getNoteResponse, Boolean, Exception>{
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

    suspend fun getUserDetailResponse(): DataExceptionHandling<getUserDetailResponse, Boolean, Exception>{
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

    suspend fun postNoteResponse(): DataExceptionHandling<postNoteResponse, Boolean, Exception>{
        try {
            postNoteExceptionHandling.loading = true
            postNoteExceptionHandling.data    = api.postNote(title = "Lorem Ipsum", description = "Lorem Ipsum")

            if(postNoteExceptionHandling.data.toString().isNotEmpty()) {
                postNoteExceptionHandling.loading = false
            }

        } catch (exception: Exception){
            postNoteExceptionHandling.e = exception
            Log.d("Repo exception", "postNoteResponse: ${postNoteExceptionHandling.e!!.localizedMessage}")
        }
        return postNoteExceptionHandling
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