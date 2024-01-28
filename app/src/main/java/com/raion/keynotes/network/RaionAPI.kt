package com.raion.keynotes.network

import com.raion.keynotes.model.DeleteNoteResponse
import com.raion.keynotes.model.GetNoteResponse
import com.raion.keynotes.model.GetUserDetailResponse
import com.raion.keynotes.model.PostLoginRequest
import com.raion.keynotes.model.PostLoginResponse
import com.raion.keynotes.model.PostNoteRequest
import com.raion.keynotes.model.PostNoteResponse
import com.raion.keynotes.model.PostRegisterRequest
import com.raion.keynotes.model.PostRegisterResponse
import com.raion.keynotes.model.PutNoteRequest
import com.raion.keynotes.model.PutNoteResponse
import com.raion.keynotes.repository.RaionAPIRepository
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RaionAPI {
    //GET
    @GET("note")
    suspend fun getNote(
        @Header("Authorization") token: String
    ): GetNoteResponse

    @GET("user")
    suspend fun getUserDetail(
        @Header("Authorization") token: String
    ) : GetUserDetailResponse


    // POST
    @POST("note")
    suspend fun postNote(
        @Header("Authorization") token: String,
        @Body request: PostNoteRequest
    ): PostNoteResponse

    @POST("register")
    suspend fun postRegister(
        @Header("Authorization") token: String,
        @Body request: PostRegisterRequest
    ): PostRegisterResponse

    @POST("login")
    suspend fun postLogin(
        @Header("Authorization") token: String,
        @Body request: PostLoginRequest
    ): PostLoginResponse

    //PUT
    @PUT("note/{noteId}")
    suspend fun putNote(
        @Path("noteId") noteId: String,
        @Header("Authorization") token: String,
        @Body request: PutNoteRequest
    ): PutNoteResponse

    //DELETE
    @DELETE("note/{noteId}")
    suspend fun deleteNote(
        @Header("Authorization") token: String,
        @Path("noteId") noteId: String
    ): DeleteNoteResponse
}