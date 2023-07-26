package com.example.flightsearch.ui.components.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.flightsearch.ui.components.Airport.AirportDestination
import com.example.flightsearch.ui.components.Airport.AirportScreen
import com.example.flightsearch.ui.components.HomeDestination
import com.example.flightsearch.ui.components.HomeScreen

@Composable
fun FlightSearchNavigation(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navHostController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToAirportPage = { navHostController.navigate("${AirportDestination.route}/${it}") }
            )
        }
        composable(
            route = AirportDestination.routeWithArgs,
            arguments = listOf(navArgument(AirportDestination.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            AirportScreen(
                onAddFavoriteBtnClicked = { navHostController.popBackStack() },
            )
        }
    }
}