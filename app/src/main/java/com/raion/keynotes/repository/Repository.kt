package com.raion.keynotes.repository

import android.util.Log
import com.raion.keynotes.data.DataExceptionHandling
import com.raion.keynotes.model.getNoteResponse
import com.raion.keynotes.model.postNoteResponse
import com.raion.keynotes.network.RaionAPI
import javax.inject.Inject

class RaionRepository @Inject constructor(private val api: RaionAPI){
    private val getNoteExceptionHandling  = DataExceptionHandling<getNoteResponse, Boolean, Exception>()
    private val postNoteExceptionHandling = DataExceptionHandling<postNoteResponse, Boolean, Exception>()

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