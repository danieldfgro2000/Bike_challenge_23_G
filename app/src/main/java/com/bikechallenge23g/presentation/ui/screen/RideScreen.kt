package com.bikechallenge23g.presentation.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.bikechallenge23g.R
import com.bikechallenge23g.data.model.Ride
import com.bikechallenge23g.presentation.navigation.NavigationRoutes
import com.bikechallenge23g.presentation.ui.composables.CustomBarChart
import com.bikechallenge23g.presentation.ui.composables.CustomDialog
import com.bikechallenge23g.presentation.ui.composables.NoItemsPlaceholder
import com.bikechallenge23g.presentation.ui.composables.RideCard
import com.bikechallenge23g.presentation.ui.composables.TopBar
import com.bikechallenge23g.presentation.viewmodel.MainViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RideScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val rides by viewModel.rides.collectAsState()
    val showTopBarIcon = rides.isNotEmpty()

    LaunchedEffect(key1 = rides) {
        viewModel.getAllRides()
        viewModel.getChartData()
    }

    val chartData by viewModel.chartData.collectAsState()

    var showRideDeleteDialog by remember { mutableStateOf(false) }
    var deletedRide by remember { mutableStateOf<Ride?>(null) }

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
                viewModel.clearSelectedRide()
                navController.navigate(NavigationRoutes.AddEditRide.route)
            }
        },
        content = {
            if (rides.isEmpty()) {
                NoItemsPlaceholder(navController = navController, isRideFlow = true)
            } else {
                LazyColumn {
                    item {
                        CustomBarChart(
                            totalKm = chartData.first,
                            values = chartData.second
//                            values = listOf(
//                                Triple(BikeType.ROAD_BIKE, 500f, AppRed),
//                                Triple(BikeType.MTB, 1400f, AppOrange),
//                                Triple(BikeType.HYBRID, 200f, AppLightGreen),
//                                Triple(BikeType.ELECTRIC, 110f, Color.White)
//                            )
                        )
                    }
                    items(rides) { currentRide ->
                        RideCard(
                            ride = currentRide,
                            bikeModel = viewModel.bikes.collectAsState().value
                                .firstOrNull { currentRide.bikeId == it.id }?.model ?: "",
                            onCardClicked = {},
                            onEditSelected = {
                                viewModel.setSelectedRide(currentRide)
                                navController.navigate(NavigationRoutes.AddEditRide.route)
                            },
                            onDeleteSelected = {
                                deletedRide = currentRide
                                showRideDeleteDialog = true
                            }
                        )
                    }
                }
            }
            if (showRideDeleteDialog) {
                deletedRide?.let {
                    CustomDialog(
                        title = it.name ?: "",
                        onCancel = { showRideDeleteDialog = false },
                        onConfirm = {
                            showRideDeleteDialog = false
                            viewModel.deleteRide(it)
                            deletedRide = null
                        }
                    )
                }
            }
        }
    )
}