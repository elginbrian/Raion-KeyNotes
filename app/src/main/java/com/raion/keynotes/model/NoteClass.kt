package com.raion.keynotes.model

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.Entity
import androidx.room.PrimaryKey

@DatabaseView
@Entity(tableName = "note_table")
data class NoteClass(
    @PrimaryKey
    var noteId: String,

    @ColumnInfo
    var title: String,

    @ColumnInfo
    var description: String,

    @ColumnInfo
    var createdAt: String,

    @ColumnInfo
    var updatedAt: String
)