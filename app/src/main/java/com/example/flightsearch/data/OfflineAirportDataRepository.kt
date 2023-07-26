package com.example.flightsearch.data

import kotlinx.coroutines.flow.Flow

class OfflineAirportDataRepository(private val flightSearchDao: FlightSearchDao) : AirportRepository {
    override fun getFlight(id: Int): Flow<Airport> =
        flightSearchDao.getFlight(id)

    override fun getAirports(searchText: String): Flow<List<Airport>> = flightSearchDao.getAirports(searchText)

    override fun getAllFavorite(): Flow<List<Favorite>> = flightSearchDao.getAllFavorites()

    override fun insertFavouriteFlight(airport: Airport) =
        flightSearchDao.insertFavoriteFlight(airport.iataCode, airport.name)

    override fun getAllFavouriteFlights(favorite: Favorite): Flow<List<Favorite>> =
        flightSearchDao.getAllFavorites()
}
