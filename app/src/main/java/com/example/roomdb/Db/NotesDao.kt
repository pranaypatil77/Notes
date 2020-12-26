package com.example.roomdb.Db

import androidx.room.*

@Dao
interface NotesDao {
    @Insert
   suspend fun addNote(notes : Notes)
    @Query("Select * From  notes order by id desc")
   suspend fun getAllNotes() : List<Notes>
    @Insert
   suspend fun addMulNotes(vararg : Notes)
    @Update
    suspend fun updateNotes(notes: Notes)
    @Delete
    suspend fun deleteNotes(notes: Notes)
}