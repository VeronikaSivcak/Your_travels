package com.example.yourtravels.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yourtravels.data.Travel
import com.example.yourtravels.data.TravelsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


//inventory app - HomeViewModel
class HomeViewModel(travelsRepository: TravelsRepository) : ViewModel() {
    val homeUiState: StateFlow<HomeUiState> =
        travelsRepository.getAllTravelsStream().map { HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class HomeUiState(val travelList: List<Travel> = listOf())