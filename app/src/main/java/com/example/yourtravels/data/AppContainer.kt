package com.example.yourtravels.data

import android.content.Context


//zdroj: https://developer.android.com/codelabs/basic-android-kotlin-compose-persisting-data-room?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fandroid-basics-compose-unit-6-pathway-2%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fbasic-android-kotlin-compose-persisting-data-room#7
interface AppContainer {
    val travelsRepository: TravelsRepository
}


class AppDataContainer(private val context: Context) : AppContainer {
    override val travelsRepository: TravelsRepository by lazy {
        OfflineTravelsRepository(YourTravelsDatabase.getDatabase(context).travelDao())

    }
}