package com.example.flightsearch

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.flightsearch.data.AppDataContainer
import com.example.flightsearch.data.FlightPreferenceRepository

private const val FLIGHT_SEARCH_PREFERENCE_NAME = "flight_search_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = FLIGHT_SEARCH_PREFERENCE_NAME
)

class FlightSearchApplication : Application() {
    lateinit var flightPreferenceRepository: FlightPreferenceRepository
    lateinit var container: AppDataContainer

    override fun onCreate() {
        super.onCreate()
        flightPreferenceRepository = FlightPreferenceRepository(dataStore)
        container = AppDataContainer(this)
    }
}