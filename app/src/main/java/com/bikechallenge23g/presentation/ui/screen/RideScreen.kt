package com.bikechallenge23g.presentation.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.bikechallenge23g.R
import com.bikechallenge23g.presentation.navigation.NavigationRoutes
import com.bikechallenge23g.presentation.ui.composables.NoItemsPlaceholder
import com.bikechallenge23g.presentation.ui.composables.TopBar
import com.bikechallenge23g.presentation.viewmodel.MainViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RideScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val rides by viewModel.rides.collectAsState()

    LaunchedEffect(key1 = rides) { viewModel.getAllRides() }

    val showTopBarIcon = rides.isNotEmpty()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopBar(
                title = R.string.rides,
                icon = if (showTopBarIcon) R.drawable.icon_add else null,
                iconDescription = if (showTopBarIcon) R.string.add_ride else null,
                showIconDescription = showTopBarIcon
            ) {
                navController.navigate(NavigationRoutes.AddEditRide.route)
            }
        },
        content = {
            if (rides.isEmpty()) {
                NoItemsPlaceholder(navController = navController, isRideFlow = true)
            } else {

            }
        }
    )
}