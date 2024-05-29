package com.example.yourtravels

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.yourtravels.add_screens.NewTravelViewModel
import com.example.yourtravels.home.HomeViewModel

//AppViewModelProvider
object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            NewTravelViewModel(yourTravelsApplication().container.travelsRepository)
        }

        initializer {
            HomeViewModel(yourTravelsApplication().container.travelsRepository)
        }
    }
}

fun CreationExtras.yourTravelsApplication(): YourTravelsApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as YourTravelsApplication)