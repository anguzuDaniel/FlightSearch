package com.example.flightsearch.ui.components

import com.example.flightsearch.data.Favorite
import com.example.flightsearch.data.Airport

// data class to stores the state of the Home page UI
data class HomePageUiState(
    var searchText: String = "",
    var hasSuggestions: Boolean = true,
    var suggestions: List<Airport> = emptyList(),
    var favorites: List<Favorite> = emptyList(),
    var isFavoriteDisplayed: Boolean = false,
)

// data class to stores the state of the Flight page UI
data class FlightUiState(
    val airports: List<Airport> = emptyList(),
)

data class FavouriteUiState(
    val favorite: List<Favorite> = emptyList(),
    val isDisplayed: Boolean = false,
)