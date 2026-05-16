package com.example.kreedaprerana.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        Athlete::class,
        Trial::class
    ],

    version = 11,

    exportSchema = false
)
abstract class SportsDatabase : RoomDatabase() {

    abstract fun sportsDao(): SportsDao
}