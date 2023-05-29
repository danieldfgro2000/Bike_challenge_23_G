package com.bikechallenge23g.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bikechallenge23g.ui.composables.BottomMenu
import com.bikechallenge23g.ui.composables.BottomMenuItem
import com.bikechallenge23g.ui.screen.BikeScreen
import com.bikechallenge23g.ui.screen.RideScreen
import com.bikechallenge23g.ui.screen.SettingScreen

@Composable
fun BikeApp(mainViewModel: MainViewModel) {
    val scrollState = rememberScrollState()
    val navController = rememberNavController()
    MainScreen(
        navController = navController,
        scrollState = scrollState,
        mainViewModel = mainViewModel
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    scrollState: ScrollState,
    mainViewModel: MainViewModel
) {
    Scaffold(bottomBar = { BottomMenu(navController = navController) }) {
        Navigation(
            navController = navController,
            scrollState = scrollState,
            paddingValues = it,
            viewModel = mainViewModel
        )
    }
}

@Composable
fun Navigation(
    navController: NavHostController,
    scrollState: ScrollState,
    paddingValues: PaddingValues,
    viewModel: MainViewModel
) {
    NavHost(
        navController = navController,
        startDestination = BottomMenuItem.Bikes.route,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {
        bottomNavigation(
            navController = navController,
            viewModel = viewModel
        )
    }
}

fun NavGraphBuilder.bottomNavigation(
    navController: NavController,
    viewModel: MainViewModel
) {
    composable(BottomMenuItem.Bikes.route) {
        BikeScreen(navController = navController, viewModel = viewModel)
    }
    composable(BottomMenuItem.Rides.route) {
        RideScreen(navController = navController, viewModel = viewModel)
    }
    composable(BottomMenuItem.Settings.route) {
        SettingScreen(navController = navController, viewModel = viewModel)
    }

}