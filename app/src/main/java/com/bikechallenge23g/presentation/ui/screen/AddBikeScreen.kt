package com.bikechallenge23g.presentation.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bikechallenge23g.R
import com.bikechallenge23g.data.model.enums.BikeTypes
import com.bikechallenge23g.data.model.enums.BikeWheels
import com.bikechallenge23g.presentation.navigation.BottomMenuItem
import com.bikechallenge23g.presentation.ui.composables.ColorRow
import com.bikechallenge23g.presentation.ui.composables.CustomButton
import com.bikechallenge23g.presentation.ui.composables.CustomSwitch
import com.bikechallenge23g.presentation.ui.composables.CustomTextField
import com.bikechallenge23g.presentation.ui.composables.DropdownSelector
import com.bikechallenge23g.presentation.ui.composables.SelectBikePager
import com.bikechallenge23g.presentation.ui.composables.TextLabel
import com.bikechallenge23g.presentation.ui.composables.TopBar
import com.bikechallenge23g.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddBikeScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val screenWidth = LocalConfiguration.current.screenHeightDp
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    val pagerState = rememberPagerState(
        initialPage = BikeTypes.ELECTRIC.ordinal,
        initialPageOffsetFraction = -0.16f
    ) { BikeTypes.values().size }

    val errorMessage = stringResource(id = R.string.required_field)
    var bikeNameError by remember { mutableStateOf<String?>(errorMessage) }
    var bikeServiceIntervalError by remember { mutableStateOf<String?>(null) }

    val newBike = viewModel.newBike.collectAsState().value
    val isInputValid: Boolean =
        newBike.model.isNotBlank() && newBike.dueService.isNotBlank()

    LaunchedEffect(key1 = pagerState.currentPage) {
        viewModel.updateNewBike(bikeType = BikeTypes.values()[pagerState.currentPage])
    }

    Scaffold(
        backgroundColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                title = R.string.add_bike,
                icon = R.drawable.icon_x,
                iconDescription = R.string.close
            ) {
                navController.popBackStack()
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .verticalScroll(scrollState)
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                ColorRow { selectedColor -> viewModel.updateNewBike(bikeColor = selectedColor) }
                SelectBikePager(
                    state = pagerState,
                    currentWidth = screenWidth,
                    bikeTypes = BikeTypes.values(),
                    wheels = viewModel.newBike.collectAsState().value.wheelSize,
                    bikeColors = viewModel.newBike.collectAsState().value.bikeColor
                )
                TextLabel(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    inputText = stringResource(id = R.string.bike_name),
                    isRequired = true
                )
                CustomTextField(
                    value = viewModel.newBike.collectAsState().value.model,
                    error = bikeNameError,
                    modifier = Modifier
                        .padding(10.dp)
                        .onGloballyPositioned {
                            coroutineScope.launch { scrollState.animateScrollTo(screenHeight) }
                        }
                ) { newBikeName ->
                    bikeNameError = if (newBikeName.isBlank()) errorMessage else null
                    viewModel.updateNewBike(model = newBikeName)
                }
                TextLabel(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    inputText = stringResource(id = R.string.wheel_size),
                    isRequired = true
                )
                DropdownSelector(
                    modifier = Modifier.padding(10.dp),
                    items = BikeWheels.values().map { wheel -> wheel.size },
                    selectedItem = viewModel.newBike.collectAsState().value.wheelSize.size,
                    onItemSelected = { selectedWheel ->
                        viewModel.updateNewBike(
                            wheelSize = BikeWheels.values()
                                .find { available -> available.size == selectedWheel }!!
                        )
                    }
                )
                TextLabel(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    inputText = stringResource(id = R.string.service_interval),
                    isRequired = true
                )
                CustomTextField(
                    value = viewModel.newBike.collectAsState().value.dueService,
                    error = bikeServiceIntervalError,
                    modifier = Modifier
                        .padding(10.dp)
                        .onGloballyPositioned {
                            coroutineScope.launch { scrollState.animateScrollTo(screenHeight) }
                        },
                    displayUnit = true,
                    unit = viewModel.distanceUnit.collectAsState().value
                ) { newServiceInterval ->
                    bikeServiceIntervalError =
                        if (newServiceInterval.isBlank()) errorMessage else null
                    viewModel.updateNewBike(dueService = newServiceInterval)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextLabel(
                        inputText = stringResource(id = R.string.default_bike)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    CustomSwitch { changeDefault -> viewModel.updateNewBike(isDefault = changeDefault) }
                }
            }
        },
        bottomBar = {
            CustomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                text = stringResource(id = R.string.add_bike),
                enabled = isInputValid
            ) {
                viewModel.saveNewBike()
                navController.navigate(BottomMenuItem.Bikes.route)
            }
        }
    )
}

