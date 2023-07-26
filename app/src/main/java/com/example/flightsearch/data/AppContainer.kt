package com.example.flightsearch.data

import android.content.Context

interface AppContainer {
    val airportRepository: AirportRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val airportRepository: AirportRepository by lazy {
        OfflineAirportDataRepository(FlightSearchDatabase.getDatabase(context).fightSearchDao())
    }
}


