package com.example.roomdb.Db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.util.concurrent.locks.Lock

@Database(
    entities = [Notes::class],
    version = 1
)
abstract class NotesDb : RoomDatabase() {
    abstract fun getNotesDao(): NotesDao

    companion object {
        @Volatile
        private var instance: NotesDb? = null
        private val Lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(Lock) {
            instance ?: buildBd(context).also {
                instance = it
            }
        }

        private fun buildBd(context: Context) = Room.databaseBuilder(
            context.applicationContext, NotesDb::class.java, "NotesDb"
        ).build()

    }
}