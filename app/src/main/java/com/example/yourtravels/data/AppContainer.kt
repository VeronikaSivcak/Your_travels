package com.example.yourtravels.data

import android.content.Context

/**
 * zdroj: https://developer.android.com/codelabs/basic-android-kotlin-compose-persisting-data-room#6
 */
interface AppContainer {
    val travelsRepository: TravelsRepository
    val expenseRepository: ExpenseRepository
}


class AppDataContainer(private val context: Context) : AppContainer {
    override val travelsRepository: TravelsRepository by lazy {
        OfflineTravelsRepository(YourTravelsDatabase.getDatabase(context).travelDao())
    }
    override val expenseRepository: ExpenseRepository by lazy {
        OfflineExpenseRepository(YourTravelsDatabase.getDatabase(context).expenseDao())

    }
}