package com.example.kreedaprerana.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "trials",
    foreignKeys = [
        ForeignKey(
            entity = Athlete::class,
            parentColumns = ["id"],
            childColumns = ["athleteId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["athleteId"])]
)
data class Trial(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val athleteId: Int,
    val trialType: String,
    val value: Double,
    val timestamp: Long = System.currentTimeMillis()
)