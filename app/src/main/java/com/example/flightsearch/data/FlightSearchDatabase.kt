package com.example.flightsearch.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Airport::class, Favorite::class], version = 1, exportSchema = false)
abstract class FlightSearchDatabase : RoomDatabase() {
    abstract fun fightSearchDao(): FlightSearchDao

    companion object {
        @Volatile
        private var instance: FlightSearchDatabase? = null

        fun getDatabase(context: Context): FlightSearchDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(context, FlightSearchDatabase::class.java, "app_database")
                    .createFromAsset("databases/flight_search.db")
                    .build()
                    .also { instance = it }
            }
        }
    }
}