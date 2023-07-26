package com.example.flightsearch.data

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FlightSearchDao {
    @Query("SELECT * FROM favorite WHERE departure_code = :id")
    fun getFavorite(id: Int): Flow<Favorite>

    @Query("SELECT * FROM airport WHERE id = :id")
    fun getFlight(id: Int?): Flow<Airport>

    @Query("SELECT * FROM airport WHERE iata_code LIKE '%' || :search || '%' OR name LIKE '%' || :search || '%' ORDER BY name ASC")
    fun getAirports(search: String?): Flow<List<Airport>>

    @Query("SELECT * FROM favorite")
    fun getAllFavorites(): Flow<List<Favorite>>

    @Query("INSERT INTO favorite (id, departure_code, destination_code) VALUES (null, :departureCode, :destination)")
    fun insertFavoriteFlight(departureCode: String, destination: String)
}