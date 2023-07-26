package com.example.flightsearch.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "airport")
data class Airport(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @NonNull
    @ColumnInfo(name = "iata_code")
    val iataCode: String,
    @NonNull
    val name: String,
    @NonNull
    val passengers: Int,
)

@Entity(tableName = "favorite")
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @NonNull
    @ColumnInfo(name = "departure_code")
    val departureCode: String,
    @NonNull
    @ColumnInfo(name = "destination_code")
    val destinationCode: String,
)
