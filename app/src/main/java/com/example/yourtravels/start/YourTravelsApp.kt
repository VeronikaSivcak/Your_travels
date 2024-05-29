package com.example.yourtravels.start

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.yourtravels.navigation.AppNavigationGraph

@Composable
fun YourTravelsApp(navController : NavHostController = rememberNavController()) {
    AppNavigationGraph(navController = navController)
}