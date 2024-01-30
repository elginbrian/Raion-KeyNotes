package com.raion.keynotes.model

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.Entity
import androidx.room.PrimaryKey

@DatabaseView
@Entity(tableName = "token_table")
data class TokenClass(
    @PrimaryKey
    var tokenId: String,

    @ColumnInfo
    var timeStamp: String
)