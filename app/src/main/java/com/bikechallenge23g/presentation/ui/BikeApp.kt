package com.bikechallenge23g.presentation.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bikechallenge23g.presentation.navigation.BottomMenu
import com.bikechallenge23g.presentation.navigation.BottomMenuItem
import com.bikechallenge23g.presentation.navigation.NavigationRoutes
import com.bikechallenge23g.presentation.ui.screen.AddEditBikeScreen
import com.bikechallenge23g.presentation.ui.screen.AddEditRideScreen
import com.bikechallenge23g.presentation.ui.screen.BikeDetailScreen
import com.bikechallenge23g.presentation.ui.screen.BikeScreen
import com.bikechallenge23g.presentation.ui.screen.RideScreen
import com.bikechallenge23g.presentation.ui.screen.SettingScreen
import com.bikechallenge23g.presentation.viewmodel.MainViewModel
import com.bikechallenge23g.theme.BikeChallenge23GTheme

@Composable
fun BikeApp(mainViewModel: MainViewModel) {
    val bottomNavController = rememberNavController()

    MainScreen(
        bottomNavController = bottomNavController,
        mainViewModel = mainViewModel
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    bottomNavController: NavHostController,
    mainViewModel: MainViewModel
) {
    var showBottomMenu by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        bottomNavController.addOnDestinationChangedListener { _, destination, _ ->
            Log.e("Destination route = ", "${destination.route}")
            bottomNavController.currentDestination?.let {
                showBottomMenu =
                    it.route != NavigationRoutes.AddEditBike.route &&
                            it.route != NavigationRoutes.AddEditRide.route &&
                            it.route != NavigationRoutes.BikeDetails.route
            }
        }
    }
    BikeChallenge23GTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            Scaffold(
                bottomBar = {
                    if (showBottomMenu) {
                        BottomMenu(navController = bottomNavController)
                    }
                }
            ) {
                Navigation(
                    bottomNavController = bottomNavController,
                    paddingValues = it,
                    viewModel = mainViewModel
                )
            }
        }
    }
}

@Composable
fun Navigation(
    bottomNavController: NavHostController,
    paddingValues: PaddingValues,
    viewModel: MainViewModel
) {
    NavHost(
        navController = bottomNavController,
        startDestination = BottomMenuItem.Bikes.route,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {
        bottomNavigation(
            navController = bottomNavController,
            viewModel = viewModel
        )
        extendedNavigation(
            navController = bottomNavController,
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
        SettingScreen(viewModel = viewModel)
    }
}

fun NavGraphBuilder.extendedNavigation(
    navController: NavController,
    viewModel: MainViewModel
) {

    composable(NavigationRoutes.AddEditBike.route) {
        AddEditBikeScreen(navController = navController, viewModel = viewModel)
    }
    composable(NavigationRoutes.AddEditRide.route) {
        AddEditRideScreen(navController = navController, viewModel = viewModel)
    }
    composable(NavigationRoutes.BikeDetails.route) {
        BikeDetailScreen(navController = navController, viewModel = viewModel)
    }

}