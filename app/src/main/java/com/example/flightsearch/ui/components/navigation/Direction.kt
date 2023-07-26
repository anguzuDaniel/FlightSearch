package com.example.flightsearch.ui.components.navigation

enum class Direction {
    HOME, AIRPORT_PAGE
}

interface NavigationDestination {
  val route: String
  val name: Int
}