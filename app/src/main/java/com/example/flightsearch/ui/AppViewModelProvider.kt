package com.example.flightsearch.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearch.FlightSearchApplication
import com.example.flightsearch.ui.components.Airport.AirportScreen
import com.example.flightsearch.ui.components.Airport.AirportViewModel
import com.example.flightsearch.ui.components.FavoriteViewModel
import com.example.flightsearch.ui.components.HomePageViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for HomePageViewModel
        initializer {
            HomePageViewModel(
                flightPreferenceRepository = flightSearchApplication().container.airportRepository
            )
        }

        initializer {
            FavoriteViewModel(
                airportRepository = flightSearchApplication().container.airportRepository
            )
        }

        initializer {
            AirportViewModel(
                this.createSavedStateHandle(),
                flightSearchApplication().container.airportRepository
            )
        }
    }
}


fun CreationExtras.flightSearchApplication(): FlightSearchApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as FlightSearchApplication)