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
import com.raion.keynotes.util.Token
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RaionAPI {
    //GET
    @GET("note")
    @Headers("Authorization: Bearer ${Token.TOKEN_STRING}")
    suspend fun getNote(): GetNoteResponse

    @GET("user")
    @Headers("Authorization: Bearer ${Token.TOKEN_STRING}")
    suspend fun getUserDetail() : GetUserDetailResponse


    // POST
    @POST("note")
    @Headers("Authorization: Bearer ${Token.TOKEN_STRING}")
    suspend fun postNote(@Body request: PostNoteRequest): PostNoteResponse

    @POST("register")
    @Headers("Authorization: Bearer ${Token.TOKEN_STRING}")
    suspend fun postRegister(@Body request: PostRegisterRequest): PostRegisterResponse

    @POST("login")
    @Headers("Authorization: Bearer ${Token.TOKEN_STRING}")
    suspend fun postLogin(@Body request: PostLoginRequest): PostLoginResponse

    //PUT
    @PUT("note/{noteId}")
    @Headers("Authorization: Bearer ${Token.TOKEN_STRING}")
    suspend fun putNote(@Path("noteId") noteId: String, @Body request: PutNoteRequest): PutNoteResponse

    //DELETE
    @DELETE("note/{noteId}")
    @Headers("Authorization: Bearer ${Token.TOKEN_STRING}")
    suspend fun deleteNote(@Path("noteId") noteId: String): DeleteNoteResponse
}