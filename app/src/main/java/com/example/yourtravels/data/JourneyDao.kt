package com.example.yourtravels.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

@Dao
interface JourneyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(journey : Journey)

    @Update
    suspend fun update(journey : Journey)

    @Delete
    suspend fun delete(journey : Journey)
}