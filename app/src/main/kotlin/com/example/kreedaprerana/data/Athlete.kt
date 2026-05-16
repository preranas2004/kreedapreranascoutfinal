package com.example.kreedaprerana.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "athletes")
data class Athlete(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val age: Int,
    val primarySport: String
)