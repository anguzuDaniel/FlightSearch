package com.example.flightsearch.ui.components.Airport

import com.example.flightsearch.data.Airport

data class AirportUiState(
    val id: Int = 0,
    val iataCode: String = "",
    val name: String = "",
    val passengers: Int = 0,
    val isFavorite: Boolean = false
)