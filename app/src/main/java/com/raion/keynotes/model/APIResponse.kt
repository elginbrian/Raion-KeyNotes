package com.raion.keynotes.model

import com.google.gson.annotations.SerializedName

// GET NOTE
data class getNoteResponse(
    @SerializedName("error")   val error: Boolean,
    @SerializedName("status")  val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("count")   val count: Int,
    @SerializedName("data")    val data: List<noteItem>
)

data class noteItem(
    @SerializedName("noteId")      val noteId: String,
    @SerializedName("title")       val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("createdAt")   val createdAt: String,
    @SerializedName("updatedAt")   val updatedAt: String
)


// GET NOTE DETAIL
data class getNoteDetailResponse(
    @SerializedName("error")   val error: Boolean,
    @SerializedName("status")  val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("data")    val data: noteDetailItem
)

data class noteDetailItem(
    @SerializedName("noteId")      val noteId: String,
    @SerializedName("title")       val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("createdAt")   val createdAt: String,
    @SerializedName("updatedAt")   val updatedAt: String
)


// GET USER DETAIL
data class getUserDetailResponse(
    @SerializedName("error")   val error: Boolean,
    @SerializedName("status")  val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("data")    val data: userDetailItem
)

data class userDetailItem(
    @SerializedName("id")       val id: String,
    @SerializedName("name")     val name: String,
    @SerializedName("password") val password: String,
    @SerializedName("salt")     val salt: String
)


// POST NOTE
data class postNoteResponse(
    @SerializedName("error")   val error: Boolean,
    @SerializedName("status")  val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("data")    val data: String
)