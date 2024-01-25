package com.raion.keynotes.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.raion.keynotes.model.NoteClass
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDAO {
    @Query("SELECT * from note_table")
    fun getNote(): Flow<List<NoteClass>>

    @Query("SELECT * from note_table where noteId=:noteId")
    suspend fun getNoteId(noteId: String): NoteClass

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(NoteClass: NoteClass)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(NoteClass: NoteClass)

    @Query("DELETE from note_table")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteNote(NoteClass: NoteClass)
}