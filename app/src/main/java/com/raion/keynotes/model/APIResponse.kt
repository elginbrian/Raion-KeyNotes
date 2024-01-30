package com.raion.keynotes.model

import com.google.gson.annotations.SerializedName

// GET NOTE
data class GetNoteResponse(
    @SerializedName("error")   val error: Boolean,
    @SerializedName("status")  val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("count")   val count: Int,
    @SerializedName("data")    val data: List<NoteItem>
)
data class NoteItem(
    @SerializedName("noteId")      val noteId: String,
    @SerializedName("title")       val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("createdAt")   val createdAt: String,
    @SerializedName("updatedAt")   val updatedAt: String
)


// GET USER DETAIL
data class GetUserDetailResponse(
    @SerializedName("error")   val error: Boolean,
    @SerializedName("status")  val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("data")    val data: UserDetailItem
)
data class UserDetailItem(
    @SerializedName("id")       val id: String,
    @SerializedName("name")     val name: String,
    @SerializedName("password") val password: String,
    @SerializedName("salt")     val salt: String
)


// GET NOTE DETAIL
data class GetNoteDetailResponse(
    @SerializedName("error") val error: Boolean,
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: NoteDetailItem
)

data class NoteDetailItem(
    @SerializedName("noteId") val noteId: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String
)


// POST NOTE
data class PostNoteResponse(
    @SerializedName("error")   val error: Boolean,
    @SerializedName("status")  val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("data")    val data: String
)
data class PostNoteRequest(
    @SerializedName("title")       val title: String,
    @SerializedName("description") val description: String
)

// POST REGISTER
data class PostRegisterResponse(
    @SerializedName("error")   val error: Boolean,
    @SerializedName("status")  val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("data")    val data: String
)
data class PostRegisterRequest(
    @SerializedName("nim")         val nim: String,
    @SerializedName("name")        val name: String,
    @SerializedName("password")    val password: String,
    @SerializedName("description") val description: String
)

// POST LOGIN
data class PostLoginResponse(
    @SerializedName("error")   val error: Boolean,
    @SerializedName("status")  val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("data")    val data: LoginItem
)
data class PostLoginRequest(
    @SerializedName("nim")      val nim: String,
    @SerializedName("password") val password: String
)
data class LoginItem(
    @SerializedName("token") val token: String
)

// PUT NOTE
data class PutNoteResponse(
    @SerializedName("error")   val error: Boolean,
    @SerializedName("status")  val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("data")    val data: String
)

data class PutNoteRequest(
    @SerializedName("title")       val title: String,
    @SerializedName("description") val description: String
)

// PUT USER DETAIL
data class PutUserDetailResponse(
    @SerializedName("error")   val error: Boolean,
    @SerializedName("status")  val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("data")    val data: String
)

data class PutUserDetailRequest(
    @SerializedName("name")        val name: String,
    @SerializedName("password")    val password: String,
    @SerializedName("description") val description: String
)

// DELETE NOTE
data class DeleteNoteResponse(
    @SerializedName("error")   val error: Boolean,
    @SerializedName("status")  val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("data")    val data: String
)