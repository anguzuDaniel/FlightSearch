package com.example.flightsearch.ui.components.Airport

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsearch.data.Airport
import com.example.flightsearch.data.AirportRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AirportViewModel(
    savedStateHandle: SavedStateHandle,
    private val airportRepository: AirportRepository
) : ViewModel() {
    var uiState by mutableStateOf(AirportUiState())
        private set


    private val itemId: Int = checkNotNull(savedStateHandle[AirportDestination.itemIdArg])

    init {
        viewModelScope.launch {
            uiState = airportRepository
                .getFlight(itemId)
                .filterNotNull()
                .first()
                .toAirportUiState(true)
        }
    }


    fun onAddFavoriteClicked() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val currentAirport = airportRepository
                    .getFlight(itemId)
                    .filterNotNull()
                    .first()
                airportRepository.insertFavouriteFlight(currentAirport)
                uiState = uiState.copy(isFavorite = true)
            }
        }
    }

}

fun Airport.toAirportUiState(isEntryValid: Boolean = true) = AirportUiState(
    id = id,
    iataCode = iataCode,
    name = name,
    passengers = passengers,
    isFavorite = false,
)


fun AirportUiState.toAirport() = Airport(
    id = id,
    iataCode = iataCode,
    name = name,
    passengers = passengers,
)