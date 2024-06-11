package com.example.yourtravels.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.yourtravels.screens.AddExpense
import com.example.yourtravels.screens.AddTravel
import com.example.yourtravels.screens.InfoAboutTravel
import com.example.yourtravels.screens.InfoTravelScreen
import com.example.yourtravels.screens.NewExpenseScreen
import com.example.yourtravels.screens.NewTravelScreen
import com.example.yourtravels.home.Home
import com.example.yourtravels.home.HomeScreen


/**
 * Navigačný graf pre aplikáciu
 * Robené podľa codelabu o Inventory
 */
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
                navigateToItemEntry = {navController.navigate(AddTravel.route)},
                navigateToTravelDetails = {navController.navigate("${InfoAboutTravel.route}/${it}")}
            )
        }
        composable(route = AddTravel.route) {
            NewTravelScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(route = InfoAboutTravel.routeWithParam,
            arguments = listOf(navArgument(InfoAboutTravel.travelId) {
                type = NavType.IntType
            })
        ){
            InfoTravelScreen(
                navigateBack = {navController.popBackStack()},
                navigateToAddExpense = {navController.navigate("${AddExpense.route}/$it")}
            )
        }
        composable(route = AddExpense.routeWithParam,
            arguments = listOf(navArgument(AddExpense.travelId) {
            type = NavType.IntType
            })
        ) {
            val travelId = it.arguments?.getInt(AddExpense.travelId) ?: -1
            NewExpenseScreen(
                travelId = travelId,
                navigateBack = { navController.popBackStack()}
            )
        }
    }
}