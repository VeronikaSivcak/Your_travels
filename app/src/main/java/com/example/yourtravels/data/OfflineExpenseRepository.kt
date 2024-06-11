package com.example.yourtravels.data

import kotlinx.coroutines.flow.Flow


/**
 * zdroj: https://developer.android.com/codelabs/basic-android-kotlin-compose-persisting-data-room#6
 */
class OfflineExpenseRepository (private val expenseDao: ExpenseDao) : ExpenseRepository {

    override fun getExpensesForTravelStream(id: Int): Flow<List<Expense>> = expenseDao.getExpensesForTravel(id)

    override  suspend fun insertExpense(expense: Expense) = expenseDao.insert(expense)

    override suspend fun deleteExpense(expense: Expense) = expenseDao.delete(expense)

    override suspend fun updateExpense(expense: Expense) = expenseDao.update(expense)

}