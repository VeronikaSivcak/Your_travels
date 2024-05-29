package com.example.yourtravels.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//zdroj: https://developer.android.com/codelabs/basic-android-kotlin-compose-persisting-data-room?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fandroid-basics-compose-unit-6-pathway-2%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fbasic-android-kotlin-compose-persisting-data-room#6

@Database(entities = [Travel::class], version = 1, exportSchema = false)
abstract class YourTravelsDatabase : RoomDatabase() {

    abstract fun travelDao() : TravelDao
    companion object {
        @Volatile
        private var Instance: YourTravelsDatabase? = null

        fun getDatabase(context: Context): YourTravelsDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, YourTravelsDatabase::class.java, "travel_database")
                    .build()
                    .also{ Instance = it}
            }
        }
    }
}

