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


// GET NOTE DETAIL
data class GetNoteDetailResponse(
    @SerializedName("error")   val error: Boolean,
    @SerializedName("status")  val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("data")    val data: NoteDetailItem
)

data class NoteDetailItem(
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


// DELETE NOTE
data class DeleteNoteResponse(
    @SerializedName("error")   val error: Boolean,
    @SerializedName("status")  val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("data")    val data: String
)