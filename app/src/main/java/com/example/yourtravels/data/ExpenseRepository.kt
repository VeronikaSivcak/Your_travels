package com.example.yourtravels.data

import kotlinx.coroutines.flow.Flow


/**
 * zdroj: https://developer.android.com/codelabs/basic-android-kotlin-compose-persisting-data-room#6
 */
interface ExpenseRepository {

    fun getExpensesForTravelStream(id: Int): Flow<List<Expense>>

    suspend fun insertExpense(expense: Expense)

    suspend fun deleteExpense(expense: Expense)

    suspend fun updateExpense(expense: Expense)
}