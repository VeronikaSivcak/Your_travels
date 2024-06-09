package com.example.yourtravels.add_screens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yourtravels.data.TravelsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class InfoTravelViewModel(
    private val travelsRepository: TravelsRepository,
    savedStateHandle: SavedStateHandle) : ViewModel() {

    private val itemId: Int = checkNotNull(savedStateHandle[InfoAboutTravel.travelId])


    val uiState: StateFlow<InfoTravelUiState> =
        travelsRepository.getTravelStream(itemId)
            .filterNotNull()
            .map {
                InfoTravelUiState(travelInfo = it.toTravelInfo())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = InfoTravelUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}


data class InfoTravelUiState(
    val travelInfo: TravelInfo = TravelInfo()
)