package com.example.yourtravels.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Databáza mojej aplikácie a jej migrácie
 * zdroje:
 * https://developer.android.com/codelabs/basic-android-kotlin-compose-persisting-data-room?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fandroid-basics-compose-unit-6-pathway-2%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fbasic-android-kotlin-compose-persisting-data-room#6
 * https://developer.android.com/training/data-storage/room/migrating-db-versions
 */
@Database(entities = [Travel::class, Expense::class], version = 5, exportSchema = false)
abstract class YourTravelsDatabase : RoomDatabase() {

    abstract fun travelDao() : TravelDao
    abstract fun expenseDao() : ExpenseDao
    companion object {
        @Volatile
        private var Instance: YourTravelsDatabase? = null

        fun getDatabase(context: Context): YourTravelsDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, YourTravelsDatabase::class.java, "travel_database")
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5)
                    .fallbackToDestructiveMigration()
                    .build()
                    .also{ Instance = it}
            }
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE listOfTravels ADD COLUMN notes TEXT")
            }

        }


       private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("CREATE TABLE IF NOT EXISTS `expenses` " +
                        "(`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        "`travelId` INTEGER NOT NULL, `price` REAL NOT NULL, " +
                        "`category` TEXT NOT NULL, `paymentMethod` TEXT NOT NULL, " +
                        "`notes` TEXT NULL, " +
                        " FOREIGN KEY(`travelId`) REFERENCES `listOfTravels`(`id`) " +
                        "ON UPDATE CASCADE ON DELETE CASCADE)" )
            }
        }

        private val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE expenses ADD COLUMN date TEXT NOT NULL")
            }
        }

        private val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE expenses ADD COLUMN name TEXT NOT NULL")
            }
        }

    }
}

