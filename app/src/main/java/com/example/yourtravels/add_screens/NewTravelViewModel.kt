package com.example.yourtravels.add_screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.yourtravels.data.Travel
import com.example.yourtravels.data.TravelsRepository


//ItemEntryViewModel
class NewTravelViewModel(private val travelsRepository: TravelsRepository) : ViewModel() {
    var travelUiState by mutableStateOf(TravelUiState())
        private set


    fun updateUiState(travelInfo: TravelInfo) {
        travelUiState = TravelUiState(travelInfo = travelInfo, validEntry = controlInput(travelInfo))
    }

    private fun controlInput(uiState: TravelInfo = travelUiState.travelInfo) : Boolean {
        return with(uiState) {
            name.isNotBlank() && budget.isNotBlank() && ((budget.toDoubleOrNull() ?: 0.0) > 0.0)
                    && startDate.isNotBlank() && endDate.isNotBlank()
        }
    }

    suspend fun saveTravel() {
        if(controlInput()) {
            travelsRepository.insertTravel(travelUiState.travelInfo.toTravel())
        }
    }
}



data class TravelUiState(
    val travelInfo: TravelInfo = TravelInfo(),
    val validEntry: Boolean = false
)

data class TravelInfo(
    val id: Int = 0,
    val name: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val budget: String = "",
    val notes: String? = null
)

fun TravelInfo.toTravel(): Travel = Travel(
    id = id,
    name = name,
    startDate = startDate,
    endDate = endDate,
    budget = budget.toDoubleOrNull() ?: 0.0,
    notes = notes

)

fun Travel.toTravelInfo(): TravelInfo = TravelInfo(
    id = id,
    name = name,
    startDate = startDate,
    endDate = endDate,
    budget = budget.toString(),
    notes = notes
)

