package com.example.flightsearch.data

import kotlinx.coroutines.flow.Flow

interface AirportRepository {
    fun getFlight(id: Int): Flow<Airport?>
    fun getAirports(searchText: String): Flow<List<Airport>>
    fun getAllFavorite(): Flow<List<Favorite>>
    fun insertFavouriteFlight(airport: Airport)
    fun getAllFavouriteFlights(favorite: Favorite): Flow<List<Favorite>>
}