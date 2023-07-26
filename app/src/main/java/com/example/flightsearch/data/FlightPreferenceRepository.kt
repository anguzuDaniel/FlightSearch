package com.example.flightsearch.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


class FlightPreferenceRepository(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        val Search_Text = stringPreferencesKey("")
    }

    val searchText: Flow<String> = dataStore.data
        .catch {
            if (it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map {
            it[Search_Text] ?: ""
        }

    suspend fun onInputSearchText(searchText: String) {
        dataStore.edit {
            it[Search_Text] = searchText
        }
    }
}