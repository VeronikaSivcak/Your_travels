package com.example.yourtravels.data

import androidx.room.Entity
import androidx.room.PrimaryKey


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

/*@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val travelId: Int,
    val price: Double,
    val category: String,
    val paymentMethod: String,
    val notes: String
)

//urcuje vztah medzi tabulkami

data class TravelAndExpenses(
    @Embedded val travel: Travel,
    @Relation(
        parentColumn = "id",
        entityColumn = "travelId"
    )
    //zoznam vydavkov pre konkretnu cestu
    val expenses: List<Expense>
)*/

