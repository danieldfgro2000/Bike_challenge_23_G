package com.bikechallenge23g.presentation.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import com.bikechallenge23g.presentation.ui.screen.BikeScreen
import com.bikechallenge23g.presentation.ui.screen.RideScreen
import com.bikechallenge23g.presentation.ui.screen.SettingScreen
import com.bikechallenge23g.presentation.viewmodel.MainViewModel
import com.bikechallenge23g.theme.BikeChallenge23GTheme

@Composable
fun BikeApp(mainViewModel: MainViewModel) {
    val scrollState = rememberScrollState()
    val bottomNavController = rememberNavController()

    MainScreen(
        bottomNavController = bottomNavController,
        scrollState = scrollState,
        mainViewModel = mainViewModel
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    bottomNavController: NavHostController,
    scrollState: ScrollState,
    mainViewModel: MainViewModel
) {
    var showBottomMenu by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        bottomNavController.addOnDestinationChangedListener { _, _, _ ->
            showBottomMenu =
                bottomNavController.currentDestination?.route != NavigationRoutes.AddEditBike.route &&
                        bottomNavController.currentDestination?.route != NavigationRoutes.AddEditRide.route
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
                    scrollState = scrollState,
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
    scrollState: ScrollState,
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
        SettingScreen(navController = navController, viewModel = viewModel)
    }
}

fun NavGraphBuilder.extendedNavigation(
    navController: NavController,
    viewModel: MainViewModel
) {
    composable(NavigationRoutes.AddEditBike.route) {
        AddEditBikeScreen(navController = navController, viewModel = viewModel)
    }
}