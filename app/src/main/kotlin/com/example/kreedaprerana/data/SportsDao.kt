package com.example.kreedaprerana.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SportsDao {

    @Query("SELECT * FROM athletes")
    fun getAllAthletes(): Flow<List<Athlete>>

    @Insert
    suspend fun insertAthlete(athlete: Athlete): Long

    @Insert
    suspend fun insertTrial(trial: Trial)

    @Query("SELECT * FROM trials WHERE athleteId = :athleteId ORDER BY timestamp DESC")
    fun getTrialsForAthlete(athleteId: Int): Flow<List<Trial>>
}