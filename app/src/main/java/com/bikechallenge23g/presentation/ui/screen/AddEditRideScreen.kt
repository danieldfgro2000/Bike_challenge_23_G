package com.bikechallenge23g.presentation.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bikechallenge23g.R
import com.bikechallenge23g.data.model.enums.DistanceUnit
import com.bikechallenge23g.presentation.ui.composables.CustomButton
import com.bikechallenge23g.presentation.ui.composables.CustomTextField
import com.bikechallenge23g.presentation.ui.composables.DropdownSelector
import com.bikechallenge23g.presentation.ui.composables.TextLabel
import com.bikechallenge23g.presentation.ui.composables.TopBar
import com.bikechallenge23g.presentation.ui.composables.customDateTimePicker
import com.bikechallenge23g.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AddEditRideScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    var offset by remember { mutableIntStateOf(0) }
    val scrollState = rememberScrollState()

    val requiredFieldErrorMessage = stringResource(id = R.string.required_field)
    val invalidInputErrorMessage = stringResource(id = R.string.number_input_error)

    var distanceError by remember { mutableStateOf<String?>(requiredFieldErrorMessage) }
    var bikeModelError by remember { mutableStateOf<String?>(requiredFieldErrorMessage) }

    val bikes by viewModel.bikes.collectAsState()

    val selectedBike by viewModel.selectedBike.collectAsState()
    val selectedRide by viewModel.selectedRide.collectAsState()

    if ((selectedRide?.bikeId ?: -1) == selectedBike?.id) {
        bikeModelError = null
    }
    if (selectedRide?.distance != 0.0 && selectedRide?.distance != null) {
        distanceError = null
    }

    var isInputValid = false
    selectedRide?.let { r ->
        isInputValid =
            r.bikeId != null &&
                    r.distance != 0.0 &&
                    r.distance != null &&
                    r.duration != 0 &&
                    r.date != 0L

    }

    Scaffold(
        backgroundColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                title = if (selectedRide?.id == null) R.string.add_ride else R.string.edit_ride,
                icon = R.drawable.icon_x,
                iconDescription = R.string.close
            ) {
                viewModel.clearSelectedRide()
                viewModel.clearSelectedBike()
                navController.popBackStack()
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(scrollState)
                    .onGloballyPositioned { offset = it.positionInRoot().y.toInt() - 100 }
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused) {
                            coroutineScope.launch {
                                delay(100)
                                scrollState.animateScrollTo(offset)
                            }
                        }
                    }
            ) {
                TextLabel(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    inputText = stringResource(id = R.string.ride_title)
                )
                CustomTextField(
                    modifier = Modifier.padding(10.dp),
                    value = selectedRide?.name ?: "",
                    onValueChange = { newName ->
                        viewModel.updateSelectedRide(name = newName)
                    }
                )
                TextLabel(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    inputText = stringResource(id = R.string.bike),
                    isRequired = true
                )
                DropdownSelector(
                    modifier = Modifier.padding(10.dp),
                    items = bikes.map { it.model ?: "" },
                    error = bikeModelError,
                    selectedItem = selectedBike?.model ?: "",
                    onItemSelected = { newBikeModel ->
                        val newBike = bikes.firstOrNull { it.model == newBikeModel }
                        viewModel.updateBike(
                            selected = true,
                            bike = newBike
                        )
                        viewModel.updateSelectedRide(
                            bikeId = newBike?.id,
                            distanceUnit = newBike?.distanceUnit
                        )
                    }
                )
                TextLabel(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    inputText = stringResource(id = R.string.distance),
                    isRequired = true
                )
                CustomTextField(
                    modifier = Modifier.padding(10.dp),
                    value = (selectedRide?.distance ?: 0.0).toString(),
                    error = distanceError,
                    displayUnit = true,
                    unit = selectedBike?.distanceUnit ?: DistanceUnit.KM,
                    onValueChange = { newDistance ->
                        distanceError =
                            if (newDistance.isBlank() || newDistance.toDoubleOrNull() == 0.0) {
                                requiredFieldErrorMessage
                            } else if (newDistance.toDoubleOrNull() == null) {
                                invalidInputErrorMessage
                            } else null

                        if (newDistance.isNotBlank() && newDistance.toDoubleOrNull() != null) {
                            viewModel.updateBike(
                                selected = true,
                                distance = (selectedBike?.distance ?: 0.0) + newDistance.toDouble(),
                            )
                            viewModel.updateSelectedRide(
                                distance = newDistance.toDouble()
                            )
                        }
                    }
                )
                TextLabel(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    inputText = stringResource(id = R.string.duration),
                    isRequired = true
                )
                customDateTimePicker(
                    modifier = Modifier.padding(10.dp),
                    selectedTime = selectedRide?.duration,
                    onTimeSelected = { viewModel.updateSelectedRide(duration = it) }
                )
                TextLabel(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    inputText = stringResource(id = R.string.date),
                    isRequired = true
                )
                customDateTimePicker(
                    modifier = Modifier.padding(10.dp),
                    isDatePicker = true,
                    selectedDate = selectedRide?.date,
                    onDateSelected = { viewModel.updateSelectedRide(date = it) }
                )
            }
        },
        bottomBar = {
            CustomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                text = stringResource(
                    id =
                    if (selectedRide?.id == null) R.string.add_ride
                    else R.string.save
                ),
                enabled = isInputValid
            ) {
                with(viewModel) {
                    saveSelectedRide()
                    saveSelectedBike(clear = false)
                    getAllBikes()
                    getAllRides()
                }
                navController.popBackStack()
            }
        }
    )
}