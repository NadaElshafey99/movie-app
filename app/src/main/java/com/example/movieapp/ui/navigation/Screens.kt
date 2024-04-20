package com.example.movieapp.ui.navigation

sealed class Screens(val route: String) {
    data object HomeScreen: Screens(route = "home_screen")
    data object DetailsScreen: Screens(route = "details_screen")

}