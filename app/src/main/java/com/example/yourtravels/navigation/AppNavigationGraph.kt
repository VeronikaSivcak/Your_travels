package com.example.yourtravels.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.yourtravels.add_screens.AddTravel
import com.example.yourtravels.add_screens.NewTravelScreen
import com.example.yourtravels.home.Home
import com.example.yourtravels.home.HomeScreen

@Composable
fun AppNavigationGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Home.route,
        modifier = modifier
    ) {
        composable(route = Home.route) {
            HomeScreen(
                navigateToItemEntry = {navController.navigate(AddTravel.route)}
            )
        }
        composable(route = AddTravel.route) {
            NewTravelScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}