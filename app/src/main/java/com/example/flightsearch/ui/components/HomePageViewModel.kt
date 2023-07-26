package com.example.flightsearch.ui.components

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsearch.data.AirportRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomePageViewModel(
    private val flightPreferenceRepository: AirportRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomePageUiState())

    var uiState by mutableStateOf(HomePageUiState())
        private set

    val suggestedFlights: StateFlow<FlightUiState> = flightPreferenceRepository
        .getAirports(searchText = uiState.searchText)
        .filterNotNull()
        .map {
            FlightUiState(airports = it)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = FlightUiState()
        )

    val favorites =
        flightPreferenceRepository.getAllFavorite().filterNotNull().map {
            FavouriteUiState(favorite = it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = FavouriteUiState()
        )

    fun getSuggestions() = flightPreferenceRepository.getAirports(uiState.searchText)


    fun getAllFavorites() = flightPreferenceRepository.getAllFavorite()


    fun onInputSearchText(homePageUiState: HomePageUiState) {
        viewModelScope.launch {
            uiState = HomePageUiState(
                searchText = homePageUiState.searchText,
            )
        }

        Log.d("HomePageViewModel", "onInputSearchText: ${uiState.searchText}")
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

class FavoriteViewModel(
    private val airportRepository: AirportRepository,
) : ViewModel() {
//    val flightUiState: StateFlow<FlightUiState> = flightRepository.getAllFavorite().map {
//        FavoriteCard(it)
//    }.stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
//        initialValue = FavouriteUiState()
//    )


    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}