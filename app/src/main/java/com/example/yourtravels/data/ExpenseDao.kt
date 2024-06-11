package com.example.yourtravels.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * zdroj https://developer.android.com/codelabs/basic-android-kotlin-compose-persisting-data-room#6
 */
@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(expense: Expense)
    @Update
    suspend fun update(expense: Expense)
    @Delete
    suspend fun delete(expense: Expense)

    @Query("SELECT * FROM expenses WHERE travelId = :travelId")
    fun getExpensesForTravel(travelId: Int): Flow<List<Expense>>
}