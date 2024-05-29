package com.example.yourtravels.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "listOfTravels")
data class Travel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val startDate: String,
    val endDate: String,
    val budget: Double,
   // val notes: String
)

