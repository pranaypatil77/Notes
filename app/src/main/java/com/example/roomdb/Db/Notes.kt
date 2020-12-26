package com.example.roomdb.Db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Notes (
    val title : String,
    val notes: String
):Serializable{
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}