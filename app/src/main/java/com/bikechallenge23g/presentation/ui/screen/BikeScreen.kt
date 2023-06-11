package com.bikechallenge23g.presentation.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.bikechallenge23g.R
import com.bikechallenge23g.data.model.Bike
import com.bikechallenge23g.data.model.enums.BikeColor
import com.bikechallenge23g.presentation.navigation.NavigationRoutes
import com.bikechallenge23g.presentation.ui.composables.BikeCardWithDetails
import com.bikechallenge23g.presentation.ui.composables.CustomDialog
import com.bikechallenge23g.presentation.ui.composables.NoItemsPlaceholder
import com.bikechallenge23g.presentation.ui.composables.TopBar
import com.bikechallenge23g.presentation.viewmodel.MainViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BikeScreen(
    navController: NavController,
    viewModel: MainViewModel
) {


    val defaultBike by viewModel.defaultBike.collectAsState()
    val defaultBikeChanged by viewModel.defaultBikeChanged.collectAsState()
    val bikes by viewModel.bikes.collectAsState()
    val showTopBarIcon = bikes.isNotEmpty()

//    var showDefaultBike by remember { mutableStateOf(defaultBike != null) }
    var showBikeDeleteDialog by remember { mutableStateOf(false) }
    var deletedBike by remember { mutableStateOf<Bike?>(null) }

    val systemUiController = rememberSystemUiController()
    val originalNavigationBarColor = MaterialTheme.colorScheme.background

    DisposableEffect(defaultBikeChanged) {
        if (defaultBikeChanged) {
            systemUiController.setStatusBarColor(
                color = BikeColor.ORANGE.color,
                darkIcons = true
            )
        } else {
            systemUiController.setStatusBarColor(
                color = originalNavigationBarColor,
                darkIcons = false
            )
        }


        systemUiController.setNavigationBarColor(
            color = originalNavigationBarColor,
            darkIcons = false
        )

        onDispose {
            systemUiController.setStatusBarColor(
                color = originalNavigationBarColor,
                darkIcons = false
            )
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopBar(
                title = R.string.bikes,
                icon = if (showTopBarIcon) R.drawable.icon_add else null,
                iconDescription = if (showTopBarIcon) R.string.add_bike else null,
                defaultBikeModel = defaultBike?.model,
                defaultBikeServiceIn = String.format(
                    "service in %1s%2s",
                    (defaultBike?.serviceIn ?: 0) - (defaultBike?.distance ?: 0.0),
                    defaultBike?.distanceUnit?.name
                ),
                showDefaultBikeChangedBar = defaultBikeChanged,
                showIconDescription = showTopBarIcon,
                onHideDefaultBikeBar = { viewModel.hideDefaultBikeChanged() }
            ) {
                viewModel.clearSelectedBike()
                navController.navigate(NavigationRoutes.AddEditBike.route)
            }
        },
        content = {
            if (bikes.isEmpty()) {
                NoItemsPlaceholder(navController)
            } else {
                LazyColumn {
                    items(bikes) { currentBike ->
                        BikeCardWithDetails(
                            bike = currentBike,
                            onEditSelected = {
                                viewModel.setSelectedBike(currentBike)
                                navController.navigate(NavigationRoutes.AddEditBike.route)
                            },
                            onDeleteSelected = {
                                with(viewModel) {
                                    if (currentBike.id == defaultBike?.id) {
                                        showDefaultBikeChanged()
                                    } else {
                                        hideDefaultBikeChanged()
                                    }
                                }
                                deletedBike = currentBike
                                showBikeDeleteDialog = true
                            },
                            onCardClicked = {
                                viewModel.setSelectedBike(currentBike)
                                navController.navigate(NavigationRoutes.BikeDetails.route)
                            }
                        )
                    }
                }
            }

            if (showBikeDeleteDialog) {
                deletedBike?.let {
                    CustomDialog(
                        title = it.model ?: "",
                        onCancel = { showBikeDeleteDialog = false },
                        onConfirm = {
                            showBikeDeleteDialog = false
                            viewModel.deleteBike(it)
                            deletedBike = null
                        }
                    )
                }
            }
        })


}

