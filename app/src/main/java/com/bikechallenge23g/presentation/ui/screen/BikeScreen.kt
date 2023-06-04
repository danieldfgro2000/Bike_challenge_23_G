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
import com.bikechallenge23g.data.model.Bike
import com.bikechallenge23g.presentation.navigation.NavigationRoutes
import com.bikechallenge23g.presentation.ui.composables.BikeCardWithDetails
import com.bikechallenge23g.presentation.ui.composables.CustomDialog
import com.bikechallenge23g.presentation.ui.composables.NoBikePlaceholder
import com.bikechallenge23g.presentation.ui.composables.TopBar
import com.bikechallenge23g.presentation.viewmodel.MainViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BikeScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val bikes by viewModel.bikes.collectAsState()

    LaunchedEffect(key1 = bikes) { viewModel.getAllBikes() }

    var showBikeDeleteDialog by remember { mutableStateOf(false) }
    var deletedBike by remember { mutableStateOf<Bike?>(null) }

    Scaffold(
        backgroundColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                title = R.string.bikes,
                icon = R.drawable.icon_add,
                iconDescription = R.string.add_bike,
                showIconDescription = true
            ) {
                navController.navigate(NavigationRoutes.AddBike.route)
            }
        },
        content = {
            if (bikes.isEmpty()) {
                NoBikePlaceholder(navController)
            } else {
                LazyColumn {
                    items(bikes) { bikeView ->
                        BikeCardWithDetails(
                            bike = bikeView,
                            onEditSelected = {
                                viewModel.setSelectedBike(bikeView)
                                navController.navigate(NavigationRoutes.AddBike.route)
                            },
                            onDeleteSelected = {
                                deletedBike = bikeView
                                showBikeDeleteDialog = true
                            }
                        )
                    }
                }
            }

            if (showBikeDeleteDialog) {
                deletedBike?.let {
                    CustomDialog(
                        bike = it,
                        hideDialog = { showBikeDeleteDialog = false }) { bikeToBeDeleted ->
                        showBikeDeleteDialog = false
                        deletedBike = null
                        viewModel.deleteBike(bikeToBeDeleted)
                    }
                }
            }
        })
}