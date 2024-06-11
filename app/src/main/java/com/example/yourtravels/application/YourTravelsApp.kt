package com.example.yourtravels.application

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.yourtravels.navigation.AppNavigationGraph

/**
 * robené podľa InventoryApp z codelabu Inventory
 */
@Composable
fun YourTravelsApp(navController : NavHostController = rememberNavController()) {
    AppNavigationGraph(navController = navController)
}