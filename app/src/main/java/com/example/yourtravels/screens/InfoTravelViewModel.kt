package com.example.yourtravels.screens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yourtravels.data.Expense
import com.example.yourtravels.data.ExpenseRepository
import com.example.yourtravels.data.TravelsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * viewModel pre InfoScreen
 */
class InfoTravelViewModel(
    private val travelsRepository: TravelsRepository,
    private val expenseRepository: ExpenseRepository,
    savedStateHandle: SavedStateHandle) : ViewModel() {

    private val travelId: Int = checkNotNull(savedStateHandle[InfoAboutTravel.travelId])


    private val travelFlow = travelsRepository.getTravelStream(travelId)
        .filterNotNull()
        .map { it.toTravelInfo() }


    private val expensesFlow = expenseRepository.getExpensesForTravelStream(travelId)
        .filterNotNull()


    val infoUiState: StateFlow<InfoTravelUiState> =
        combine(travelFlow, expensesFlow) { travelInfo, expenses ->
            InfoTravelUiState(
                travelInfo = travelInfo,
                expenseList = expenses,
                totalExpenses = calculateTotalExpenses(expenses),
                remainingBudget = calculateRemainingBudget(travelInfo.toTravel().budget, expenses)
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = InfoTravelUiState()
        )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    private fun calculateTotalExpenses(expenses: List<Expense>): Double {
        return if (expenses.isEmpty()) 0.0 else expenses.sumOf { it.price }
    }

    private fun calculateRemainingBudget(budget: Double, expenses: List<Expense>): Double {
        return budget - calculateTotalExpenses(expenses)
    }
}

data class InfoTravelUiState(
    val travelInfo: TravelInfo = TravelInfo(),
    val expenseList: List<Expense> = listOf(),
    val totalExpenses: Double = 0.0,
    val remainingBudget: Double = 0.0
)

