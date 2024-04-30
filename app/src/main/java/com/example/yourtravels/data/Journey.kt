package com.example.yourtravels.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "listOfJourneys")
data class Journey(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    //val startDate: Date,
    //val endDate: Date,
    val budget: Int
)