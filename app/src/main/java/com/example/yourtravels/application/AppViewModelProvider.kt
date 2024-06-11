package com.example.yourtravels.application

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.yourtravels.screens.InfoTravelViewModel
import com.example.yourtravels.screens.NewExpenseViewModel
import com.example.yourtravels.screens.NewTravelViewModel
import com.example.yourtravels.home.HomeViewModel

/**
 * Vytvára inštancie ViewModelov pre celú aplikáciu.
 * Robila som podľa: AppViewModelProvider z codelabu Inventory
 */

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            NewTravelViewModel(yourTravelsApplication().container.travelsRepository)
        }

        initializer {
            InfoTravelViewModel(yourTravelsApplication().container.travelsRepository,
                yourTravelsApplication().container.expenseRepository,
                this.createSavedStateHandle())
        }
        initializer {
            NewExpenseViewModel(yourTravelsApplication().container.expenseRepository)
        }

        initializer {
            HomeViewModel(yourTravelsApplication().container.travelsRepository)
        }
    }
}

fun CreationExtras.yourTravelsApplication(): YourTravelsApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as YourTravelsApplication)