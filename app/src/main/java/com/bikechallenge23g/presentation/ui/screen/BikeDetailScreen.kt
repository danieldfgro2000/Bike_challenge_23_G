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
import androidx.navigation.NavController
import com.bikechallenge23g.R
import com.bikechallenge23g.data.model.Bike
import com.bikechallenge23g.data.model.Ride
import com.bikechallenge23g.presentation.navigation.NavigationRoutes
import com.bikechallenge23g.presentation.ui.composables.BikeCardWithDetails
import com.bikechallenge23g.presentation.ui.composables.CustomDialog
import com.bikechallenge23g.presentation.ui.composables.RideCard
import com.bikechallenge23g.presentation.ui.composables.TopBar
import com.bikechallenge23g.presentation.viewmodel.MainViewModel

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
                title = R.string.bike_details,
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
                            BikeCardWithDetails(bike = thisBike, showMore = false)
                        }
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