package com.example.yourtravels.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TravelDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(travel : Travel)

    @Update
    suspend fun update(travel : Travel)

    @Delete
    suspend fun delete(travel : Travel)

    @Query("SELECT * from listOfTravels WHERE id = :id")
    fun getTravel(id: Int): Flow<Travel>

    @Query("SELECT * from listOfTravels")
    fun getAllTravels(): Flow<List<Travel>>
}