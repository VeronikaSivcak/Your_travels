package com.example.yourtravels.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
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
  /*  @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(expense: Expense)
    @Update
    suspend fun update(expense: Expense)
    @Delete
    suspend fun delete(expense: Expense)*/

    @Query("SELECT * FROM listOfTravels WHERE id = :id")
    fun getTravel(id: Int): Flow<Travel>

    @Query("SELECT * FROM listOfTravels")
    fun getAllTravels(): Flow<List<Travel>>

  /*  @Transaction
    @Query("SELECT * FROM listOfTravels")
    fun getTravelsWithExpenses(): List<TravelAndExpenses>

    //vrati zoznam vydavkov pre konkretnu cestu
    @Transaction
    @Query("SELECT * FROM listOfTravels WHERE id = :travelId")
    fun getTravelWithExpenses(travelId: Int): TravelAndExpenses?*/
}