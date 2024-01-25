package com.raion.keynotes.network

import com.raion.keynotes.model.getNoteDetailResponse
import com.raion.keynotes.model.getNoteResponse
import com.raion.keynotes.model.getUserDetailResponse
import com.raion.keynotes.model.postNoteResponse
import com.raion.keynotes.util.Token
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface RaionAPI {
    //GET
    @GET("note")
    @Headers("Authorization: Bearer ${Token.TOKEN_STRING}")
    suspend fun getNote(): getNoteResponse

    //@GET("note/$noteID")
    //@Headers("Authorization: Bearer ${Token.TOKEN_STRING}")
    //suspend fun getNoteDetail(): getNoteDetailResponse

    @GET("user")
    @Headers("Authorization: Bearer ${Token.TOKEN_STRING}")
    suspend fun getUserDetail() : getUserDetailResponse



    // POST
    @FormUrlEncoded
    @POST("note")
    @Headers("Authorization: Bearer ${Token.TOKEN_STRING}")
    suspend fun postNote(
        @Field("title") title: String,
        @Field("description") description: String
    ): postNoteResponse

}