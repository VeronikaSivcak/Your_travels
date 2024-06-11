package com.example.yourtravels.data

import kotlinx.coroutines.flow.Flow

/**
 * zdroj https://developer.android.com/codelabs/basic-android-kotlin-compose-persisting-data-room#6
 */

class OfflineTravelsRepository(private val travelDao: TravelDao) : TravelsRepository {
   override fun getAllTravelsStream() : Flow<List<Travel>> = travelDao.getAllTravels()

    override fun getTravelStream(id: Int): Flow<Travel?> = travelDao.getTravel(id)

    override  suspend fun insertTravel(travel: Travel) = travelDao.insert(travel)

    override suspend fun deleteTravel(travel: Travel) = travelDao.delete(travel)

    override suspend fun updateTravel(travel: Travel) = travelDao.update(travel)

}