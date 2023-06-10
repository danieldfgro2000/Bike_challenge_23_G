package com.bikechallenge23g.presentation.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bikechallenge23g.R
import com.bikechallenge23g.data.model.Bike
import com.bikechallenge23g.data.model.Ride
import com.bikechallenge23g.presentation.navigation.NavigationRoutes
import com.bikechallenge23g.presentation.ui.composables.BikeCardWithDetails
import com.bikechallenge23g.presentation.ui.composables.CustomDialog
import com.bikechallenge23g.presentation.ui.composables.RideCard
import com.bikechallenge23g.presentation.ui.composables.TextLabel
import com.bikechallenge23g.presentation.ui.composables.TopBar
import com.bikechallenge23g.presentation.viewmodel.MainViewModel
import com.bikechallenge23g.theme.AppLightGrey

@Composable
fun BikeDetailScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val selectedBike by viewModel.selectedBike.collectAsState()
    val rides = viewModel.rides.collectAsState().value.filter { it.bikeId == selectedBike?.id }
    Log.e("rides = ", "$rides")

    var showBikeDeleteDialog by remember { mutableStateOf(false) }
    var deletedBike by remember { mutableStateOf<Bike?>(null) }

    var showRideDeleteDialog by remember { mutableStateOf(false) }
    var deletedRide by remember { mutableStateOf<Ride?>(null) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopBar(
                titleStr = selectedBike?.model,
                showNavBack = true,
                showMoreOptions = true,
                onEdit = { navController.navigate(NavigationRoutes.AddEditBike.route) },
                onDelete = { showBikeDeleteDialog = true },
                onNavBack = { navController.popBackStack() }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                LazyColumn {
                    item {
                        viewModel.selectedBike.collectAsState().value?.let { thisBike ->
                            BikeCardWithDetails(
                                modifier = Modifier.padding(0.dp),
                                bike = thisBike,
                                isBikeDetailScreen = true,
                                ridesCount = rides.size.toString(),
                                totalRidesDistance = rides.sumOf { ride -> ride.distance ?: 0.0 }
                                    .toString()
                            )
                        }
                    }

                    item {
                        TextLabel(
                            modifier = Modifier.padding(start = 5.dp),
                            inputText = stringResource(id = R.string.rides).uppercase(),
                            height = 25.dp,
                            textStyle = MaterialTheme.typography.headlineLarge,
                            textColor = AppLightGrey.copy(alpha = 0.5f)
                        )
                    }
                    items(rides) { currentRide ->
                        RideCard(
                            ride = currentRide,
                            bikeModel = selectedBike?.model ?: "",
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
            if (showBikeDeleteDialog) {
                deletedBike = viewModel.selectedBike.collectAsState().value
                deletedBike?.let { bikeToBeDeleted ->
                    CustomDialog(
                        title = bikeToBeDeleted.model ?: "",
                        onCancel = { showBikeDeleteDialog = false },
                        onConfirm = {
                            showBikeDeleteDialog = false
                            viewModel.deleteBike(bikeToBeDeleted)
                            deletedBike = null
                            // Delete all rides as well?
                            navController.popBackStack()
                        }
                    )
                }
            }
            if (showRideDeleteDialog) {
                deletedRide?.let { rideToBeDeleted ->
                    CustomDialog(
                        title = rideToBeDeleted.name ?: "",
                        onCancel = { showRideDeleteDialog = false },
                        onConfirm = {
                            showRideDeleteDialog = false
                            viewModel.deleteRide(rideToBeDeleted)
                            deletedRide = null
                        }
                    )
                }
            }
        }
    )
}