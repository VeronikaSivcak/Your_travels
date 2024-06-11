package com.example.yourtravels.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.yourtravels.data.Expense
import com.example.yourtravels.data.ExpenseRepository


/**
 * robene podla https://developer.android.com/codelabs/basic-android-kotlin-compose-persisting-data-room#0
 */
class NewExpenseViewModel(private val expenseRepository: ExpenseRepository) : ViewModel() {
    var expenseUiState by mutableStateOf(ExpenseUiState())
        private set


   fun updateUiState(expenseInfo: ExpenseInfo) {
        expenseUiState = ExpenseUiState(expenseInfo = expenseInfo, validEntry = controlInput(expenseInfo))
    }

private fun controlInput(uiState: ExpenseInfo = expenseUiState.expenseInfo) : Boolean {
        return with(uiState) {
            category.isNotBlank() && price.isNotBlank() && ((price.toDoubleOrNull() ?: 0.0) > 0.0)
                    && paymentMethod.isNotBlank() && name.isNotBlank()
        }
    }


    suspend fun saveExpense() {

        if(controlInput() && expenseUiState.expenseInfo.travelId.isNotEmpty()) {
            expenseRepository.insertExpense(expenseUiState.expenseInfo.toExpense())
        }
    }
}



data class ExpenseUiState(
    val expenseInfo: ExpenseInfo = ExpenseInfo(),
    val validEntry: Boolean = false
)

data class ExpenseInfo(
    val id:Int = 0,
    val travelId: String = "",
    val price: String = "",
    val category: String = "",
    val paymentMethod: String = "",
    val notes: String? = null,
    val date: String = "",
    val name: String = ""
)

fun ExpenseInfo.toExpense(): Expense = Expense(
    id = id,
    travelId = travelId.toInt(),
    price = price.toDouble(),
    category = category,
    paymentMethod = paymentMethod,
    notes = notes,
    date = date,
    name = name
)


