package com.raion.keynotes.network

import com.raion.keynotes.model.getNoteResponse
import com.raion.keynotes.model.getUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface RaionAPI {
    @GET("note")
    @Headers("Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJSYWlvbi1CYXR0bGVwYXNzIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwLyIsImV4cCI6MTcwNjMyNzk1OCwidXNlcklkIjoiMjE1MTUwMjAwMTExMDMzMyJ9._Yt2v1-oQRs-NfIEl1p-zjal6HEbkH7g5QYoX9hdZu0")
    suspend fun getNote(): ArrayList<getNoteResponse>

    @GET("user")
    suspend fun getUser(): ArrayList<getUserResponse>
}