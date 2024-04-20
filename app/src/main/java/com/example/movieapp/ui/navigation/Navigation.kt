package com.example.movieapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.movieapp.ui.features.details_screen.view.DetailsMovieScreen
import com.example.movieapp.ui.features.home_screen.view.MovieHomeScreen


@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.HomeScreen.route) {
        composable(route = Screens.HomeScreen.route) {
            MovieHomeScreen(navController = navController)
        }
        composable(
            route = "${Screens.DetailsScreen.route}/{movieId}",
            arguments = listOf(navArgument("movieId") {
                type = NavType.IntType
            })
        ) {
            it.arguments?.getInt("movieId")
                .let { movieId -> DetailsMovieScreen(navController = navController, movieId = movieId) }
        }
    }

}