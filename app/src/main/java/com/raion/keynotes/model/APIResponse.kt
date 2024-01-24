package com.raion.keynotes.model

import com.google.gson.annotations.SerializedName

data class getNoteResponse(
    @SerializedName("error") val error: Boolean,
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("count") val count: Int,
    @SerializedName("data") val data: List<noteItem>?
)

data class noteItem(
    @SerializedName("noteId") val noteId: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String
)

data class getUserResponse(
    var error: Boolean,
    var status: String,
    var messege: String,
    var data: String // need fix
)