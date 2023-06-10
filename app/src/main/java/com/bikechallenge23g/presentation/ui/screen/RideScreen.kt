package com.bikechallenge23g.presentation.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bikechallenge23g.R
import com.bikechallenge23g.data.model.Ride
import com.bikechallenge23g.presentation.navigation.NavigationRoutes
import com.bikechallenge23g.presentation.ui.composables.CustomBarChart
import com.bikechallenge23g.presentation.ui.composables.CustomDialog
import com.bikechallenge23g.presentation.ui.composables.NoItemsPlaceholder
import com.bikechallenge23g.presentation.ui.composables.RideCard
import com.bikechallenge23g.presentation.ui.composables.TextLabel
import com.bikechallenge23g.presentation.ui.composables.TopBar
import com.bikechallenge23g.presentation.viewmodel.MainViewModel
import com.bikechallenge23g.theme.AppLightGrey
import java.time.LocalDate

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RideScreen(
    navController: NavController,
    viewModel: MainViewModel,

    ) {
    val rides by viewModel.rides.collectAsState()
    val showTopBarIcon = rides.isNotEmpty()

    val months = rides.map { LocalDate.ofEpochDay(it.date ?: 0).month }.distinct()

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp

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
                        )
                    }
                    @Composable
                    fun RenderMonthAndRide(both: Boolean, month: String, ride: Ride) {
                        if (both) {
                            TextLabel(
                                modifier = Modifier.padding(start = 5.dp),
                                inputText = month,
                                height = 25.dp,
                                textStyle = MaterialTheme.typography.headlineLarge,
                                textColor = AppLightGrey.copy(alpha = 0.5f)
                            )
                        }
                        RideCard(
                            ride = ride,
                            bikeModel = viewModel.bikes.collectAsState().value
                                .firstOrNull { ride.bikeId == it.id }?.model ?: "",
                            onEditSelected = {
                                viewModel.setSelectedRide(ride)
                                navController.navigate(NavigationRoutes.AddEditRide.route)
                            },
                            onDeleteSelected = {
                                deletedRide = ride
                                showRideDeleteDialog = true
                            }
                        )
                    }
                    itemsIndexed(rides) { index, ride ->
                        val month = LocalDate.ofEpochDay(ride.date ?: 0)
                            .month.name.uppercase()
                        when (index) {
                            0 -> RenderMonthAndRide(both = true, month = month, ride = ride)
                            else -> {
                                val prevMonth = LocalDate
                                    .ofEpochDay(rides[index - 1].date ?: 0)
                                    .month.name.uppercase()
                                RenderMonthAndRide(
                                    both = (month != prevMonth),
                                    month = month,
                                    ride = ride
                                )
                            }
                        }
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




