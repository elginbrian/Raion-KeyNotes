package com.raion.keynotes.network

import com.raion.keynotes.model.DeleteNoteResponse
import com.raion.keynotes.model.GetNoteDetailResponse
import com.raion.keynotes.model.GetNoteResponse
import com.raion.keynotes.model.GetUserDetailResponse
import com.raion.keynotes.model.PostNoteRequest
import com.raion.keynotes.model.PostNoteResponse
import com.raion.keynotes.util.Token
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface RaionAPI {
    //GET
    @GET("note")
    @Headers("Authorization: Bearer ${Token.TOKEN_STRING}")
    suspend fun getNote(): GetNoteResponse

    @GET("note/{noteId}")
    @Headers("Authorization: Bearer ${Token.TOKEN_STRING}")
    suspend fun getNoteDetail(@Path("noteId") noteId: String): GetNoteDetailResponse

    @GET("user")
    @Headers("Authorization: Bearer ${Token.TOKEN_STRING}")
    suspend fun getUserDetail() : GetUserDetailResponse


    // POST
    @POST("note")
    @Headers("Authorization: Bearer ${Token.TOKEN_STRING}")
    suspend fun postNote(@Body request: PostNoteRequest): PostNoteResponse


    //DELETE
    @DELETE("note/{noteId}")
    @Headers("Authorization: Bearer ${Token.TOKEN_STRING}")
    suspend fun deleteNote(@Path("noteId") noteId: String): DeleteNoteResponse
}