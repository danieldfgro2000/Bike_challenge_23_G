package com.bikechallenge23g.presentation.ui.screen

import android.annotation.SuppressLint
import android.util.Log
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
import com.bikechallenge23g.data.model.enums.BikeColor
import com.bikechallenge23g.data.model.enums.BikeType
import com.bikechallenge23g.data.model.enums.BikeWheel
import com.bikechallenge23g.data.model.enums.DistanceUnit
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

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddEditBikeScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val screenWidth = LocalConfiguration.current.screenHeightDp
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()


    val emptyFieldErrorMessage = stringResource(id = R.string.required_field)
    val numberErrorMessage = stringResource(id = R.string.number_input_error)
    var bikeNameError by remember { mutableStateOf<String?>(null) }
    var bikeServiceIntervalError by remember { mutableStateOf<String?>(null) }

    val selectedBike by viewModel.selectedBike.collectAsState()
    val isInputValid: Boolean =
        selectedBike?.model?.isNotBlank() == true &&
                selectedBike?.serviceInterval != null &&
                selectedBike?.serviceInterval.toString().isNotBlank()

    val pagerState = rememberPagerState(
        initialPage = selectedBike?.type?.ordinal ?: BikeType.ELECTRIC.ordinal,
        initialPageOffsetFraction = -0.16f
    ) { BikeType.values().size }

    LaunchedEffect(key1 = pagerState.currentPage) {
        viewModel.updateBike(selected = true, bikeType = BikeType.values()[pagerState.currentPage])
    }

    Scaffold(
        backgroundColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                title = if (selectedBike?.id == null) R.string.add_bike else R.string.edit_bike,
                icon = R.drawable.icon_x,
                iconDescription = R.string.close
            ) { navController.popBackStack() }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .verticalScroll(scrollState)
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                ColorRow(
                    initialColor = selectedBike?.bikeColor
                        ?: BikeColor.BLUE
                ) { selectedColor ->
                    viewModel.updateBike(
                        selected = true,
                        bikeColor = selectedColor
                    )
                }
                SelectBikePager(
                    state = pagerState,
                    currentWidth = screenWidth,
                    bikeTypes = BikeType.values(),
                    wheels = selectedBike?.wheelSize ?: BikeWheel.BIG,
                    bikeColor = selectedBike?.bikeColor ?: BikeColor.BLUE
                )
                TextLabel(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    inputText = stringResource(id = R.string.bike_name),
                    isRequired = true
                )
                CustomTextField(
                    value = viewModel.selectedBike.collectAsState().value?.model ?: "",
                    error = bikeNameError,
                    modifier = Modifier
                        .padding(10.dp)
                        .onGloballyPositioned {
                            coroutineScope.launch { scrollState.animateScrollTo(screenHeight) }
                        }
                ) { newBikeName ->
                    bikeNameError = if (newBikeName.isBlank()) emptyFieldErrorMessage else null
                    viewModel.updateBike(selected = true, model = newBikeName)
                }
                TextLabel(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    inputText = stringResource(id = R.string.wheel_size),
                    isRequired = true
                )
                DropdownSelector(
                    modifier = Modifier.padding(10.dp),
                    items = BikeWheel.values().map { wheel -> wheel.size },
                    selectedItem = selectedBike?.wheelSize?.size ?: BikeWheel.BIG.size,
                    onItemSelected = { selectedWheel ->
                        viewModel.updateBike(
                            selected = true,
                            wheelSize = BikeWheel.values()
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
                    value = (selectedBike?.serviceInterval ?: 100).toString(),
                    error = bikeServiceIntervalError,
                    modifier = Modifier
                        .padding(10.dp)
                        .onGloballyPositioned {
                            coroutineScope.launch {
                                scrollState.animateScrollTo(screenHeight)
                            }
                        },
                    displayUnit = true,
                    unit = selectedBike?.distanceUnit ?: DistanceUnit.KM
                ) { newServiceInterval ->
                    bikeServiceIntervalError =
                        if (newServiceInterval.isBlank()) {
                            emptyFieldErrorMessage
                        } else if (newServiceInterval.toIntOrNull() == null) {
                            numberErrorMessage
                        } else null
                    if (newServiceInterval.toIntOrNull() != null) {
                        viewModel.updateBike(
                            selected = true,
                            serviceIn = selectedBike?.serviceIn ?: newServiceInterval.toInt(),
                            serviceReminder = selectedBike?.serviceInterval,
                            serviceInterval = newServiceInterval.toInt()
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextLabel(inputText = stringResource(id = R.string.default_bike))
                    Spacer(modifier = Modifier.weight(1f))
                    CustomSwitch(defaultState = selectedBike?.isDefault ?: false) { changeDefault ->
                        viewModel.updateBike(selected = true, isDefault = changeDefault)
                    }
                }
            }
        },
        bottomBar = {
            CustomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                text = stringResource(id = if (selectedBike?.id == null) R.string.add_bike else R.string.save),
                enabled = isInputValid
            ) {
                Log.e("On save clic", "on click")
                viewModel.saveSelectedBike()
                navController.navigate(BottomMenuItem.Bikes.route)
            }
        }
    )
}

