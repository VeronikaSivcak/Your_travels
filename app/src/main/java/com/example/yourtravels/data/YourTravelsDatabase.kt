package com.example.yourtravels.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

//zdroj: https://developer.android.com/codelabs/basic-android-kotlin-compose-persisting-data-room?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fandroid-basics-compose-unit-6-pathway-2%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fbasic-android-kotlin-compose-persisting-data-room#6

@Database(entities = [Travel::class], version = 2, exportSchema = false)
abstract class YourTravelsDatabase : RoomDatabase() {

    abstract fun travelDao() : TravelDao
    companion object {
        @Volatile
        private var Instance: YourTravelsDatabase? = null

        fun getDatabase(context: Context): YourTravelsDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, YourTravelsDatabase::class.java, "travel_database")
                    .addMigrations(MIGRATION_1_2)
                    .fallbackToDestructiveMigration()
                    .build()
                    .also{ Instance = it}
            }
        }
        //https://developer.android.com/training/data-storage/room/migrating-db-versions
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE listOfTravels ADD COLUMN notes TEXT")
            }

        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE listOfTravels drop COLUMN notes")
            }

        }

        private val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE listOfTravels ADD COLUMN notes TEXT")
            }

        }
    }
}

