package com.raion.keynotes.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.raion.keynotes.model.NoteClass

@Database(entities = [NoteClass::class], version = 1, exportSchema = false)
abstract class NoteDatabase: RoomDatabase(){
    abstract fun NoteDAO(): NoteDAO
}