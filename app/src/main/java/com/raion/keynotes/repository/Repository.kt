package com.raion.keynotes.repository

import android.util.Log
import com.raion.keynotes.data.DataExceptionHandling
import com.raion.keynotes.model.getNoteResponse
import com.raion.keynotes.network.RaionAPI
import javax.inject.Inject

class RaionRepository @Inject constructor(private val api: RaionAPI){
    private val dataExceptionHandling = DataExceptionHandling<ArrayList<getNoteResponse>, Boolean, Exception>()

    suspend fun getNoteResponse(): DataExceptionHandling<ArrayList<getNoteResponse>, Boolean, Exception>{
        try {
            dataExceptionHandling.loading = true
            dataExceptionHandling.data    = api.getNote()

            if(dataExceptionHandling.data.toString().isNotEmpty()) {
                dataExceptionHandling.loading = false
            }

        } catch (exception: Exception){
            dataExceptionHandling.e = exception
            Log.d("Repo exception", "getNoteResponse: ${dataExceptionHandling.e!!.localizedMessage}")
        }
        return dataExceptionHandling
    }
}