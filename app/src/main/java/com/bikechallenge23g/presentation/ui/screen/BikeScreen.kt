package com.bikechallenge23g.presentation.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
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
import com.bikechallenge23g.presentation.navigation.NavigationRoutes
import com.bikechallenge23g.presentation.ui.composables.BikeCardWithDetails
import com.bikechallenge23g.presentation.ui.composables.CustomDialog
import com.bikechallenge23g.presentation.ui.composables.NoItemsPlaceholder
import com.bikechallenge23g.presentation.ui.composables.TopBar
import com.bikechallenge23g.presentation.viewmodel.MainViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BikeScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val bikes by viewModel.bikes.collectAsState()
    val showTopBarIcon = bikes.isNotEmpty()

//    LaunchedEffect(key1 = bikes) {
//        viewModel.getAllBikes()
//        viewModel.getAllRides()
//
//    }


//   val setAlarm by viewModel.setAlarm.collectAsState()
//    val context = LocalContext.current
//
//    if (setAlarm) {
//        Log.e("AlarmSetter ", "Set alarm")
//        AlarmSetter(context).scheduleAlarm()
//
//    } else {
//        Log.e("AlarmSetter ", "Cancel alarm")
//        AlarmSetter(context).cancelAlarm()
//    }


    var showBikeDeleteDialog by remember { mutableStateOf(false) }
    var deletedBike by remember { mutableStateOf<Bike?>(null) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopBar(
                title = R.string.bikes,
                icon = if (showTopBarIcon) R.drawable.icon_add else null,
                iconDescription = if (showTopBarIcon) R.string.add_bike else null,
                showIconDescription = showTopBarIcon
            ) {
                viewModel.saveSelectedBike()
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