package com.example.yourtravels.data

import kotlinx.coroutines.flow.Flow

/**
 * zdroj https://developer.android.com/codelabs/basic-android-kotlin-compose-persisting-data-room?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fandroid-basics-compose-unit-6-pathway-2%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fbasic-android-kotlin-compose-persisting-data-room#7
*/
interface TravelsRepository {
    fun getAllTravelsStream() : Flow<List<Travel>>

    fun getTravelStream(id: Int): Flow<Travel?>

    suspend fun insertTravel(travel: Travel)

    suspend fun deleteTravel(travel: Travel)

    suspend fun updateTravel(travel: Travel)

}