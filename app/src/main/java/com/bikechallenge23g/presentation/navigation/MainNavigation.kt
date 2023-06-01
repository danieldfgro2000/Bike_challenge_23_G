package com.bikechallenge23g.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun SecondNav(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    fun navigate(route: String) {
        navController.navigate(route) {
            navController.graph.startDestinationRoute?.let {
                popUpTo(route) { saveState = true }
            }
        }
    }
}