package com.example.yourtravels.data

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation


/**
 * zdroj: https://developer.android.com/training/data-storage/room/relationships
 */

/**
 * Trieda Travel predstavuje cestu a informácie o nej
 */
@Entity(tableName = "listOfTravels")
data class Travel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val startDate: String,
    val endDate: String,
    val budget: Double,
    val notes: String?
)

/**
 * Trieda Expense predstavuje výdavok
 */
@Entity(
    tableName = "expenses",
    foreignKeys = [ForeignKey(
        entity = Travel::class,
        parentColumns = ["id"],
        childColumns = ["travelId"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)


data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    @ColumnInfo(name= "travelId")
    val travelId: Int,
    val price: Double,
    val category: String,
    val paymentMethod: String,
    val notes: String?,
    val date: String,
    val name: String
)

/**
 * Trieda TravelAndExpense predsatvuje vzťah medzi Travel a Expense
 */
data class TravelAndExpenses(
    @Embedded val travel: Travel,
    @Relation(

        parentColumn = "id",
        entityColumn = "travelId"
    )

    val expensesForTravel: List<Expense>
)

