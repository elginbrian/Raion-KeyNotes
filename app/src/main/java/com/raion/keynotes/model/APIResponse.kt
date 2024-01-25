package com.raion.keynotes.model

import com.google.gson.annotations.SerializedName

// GET NOTE
data class getNoteResponse(
    @SerializedName("error") val error: Boolean,
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("count") val count: Int,
    @SerializedName("data") val data: List<noteItem>
)

data class noteItem(
    @SerializedName("noteId") val noteId: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String
)


// POST NOTE
data class postNoteResponse(
    @SerializedName("error") val error: Boolean,
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: String
)