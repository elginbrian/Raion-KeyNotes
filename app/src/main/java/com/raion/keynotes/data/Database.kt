package com.raion.keynotes.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.raion.keynotes.model.NoteClass
import com.raion.keynotes.model.TokenClass

@Database(entities = [NoteClass::class], version = 1, exportSchema = false)
abstract class NoteDatabase: RoomDatabase(){
    abstract fun NoteDAO(): NoteDAO
}

@Database(entities = [TokenClass::class], version = 4, exportSchema = false)
abstract class TokenDatabase: RoomDatabase(){
    abstract fun TokenDAO(): TokenDAO
}