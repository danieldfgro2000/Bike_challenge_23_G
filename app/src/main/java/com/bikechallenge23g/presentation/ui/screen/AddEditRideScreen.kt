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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bikechallenge23g.R
import com.bikechallenge23g.data.model.enums.DistanceUnit
import com.bikechallenge23g.presentation.navigation.BottomMenuItem
import com.bikechallenge23g.presentation.ui.composables.CustomButton
import com.bikechallenge23g.presentation.ui.composables.CustomDateTimePicker
import com.bikechallenge23g.presentation.ui.composables.CustomTextField
import com.bikechallenge23g.presentation.ui.composables.DropdownSelector
import com.bikechallenge23g.presentation.ui.composables.TextLabel
import com.bikechallenge23g.presentation.ui.composables.TopBar
import com.bikechallenge23g.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AddEditRideScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val screenWidth = LocalConfiguration.current.screenHeightDp
    val coroutineScope = rememberCoroutineScope()
    var offset by remember { mutableStateOf(0) }
    val focusRequester = remember { FocusRequester() }
    val scrollState = rememberScrollState()

    var distanceError by remember { mutableStateOf<String?>(null) }
    val durationError by remember { mutableStateOf<String?>(null) }
    val dateError by remember { mutableStateOf<String?>(null) }
    val requiredFieldErrorMessage = stringResource(id = R.string.required_field)
    val invalidInputErrorMessage = stringResource(id = R.string.number_input_error)
    val selectedRide = viewModel.selectedRide.collectAsState().value

    val bikes = viewModel.bikes.collectAsState().value

    var isInputValid = false
    selectedRide?.let {
        with(selectedRide) {
            isInputValid =
                bikeId != null && distance != 0.0 && distance != null && duration != 0 && date != 0L
        }
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
                    selectedItem = bikes.firstOrNull { it.id == selectedRide?.bikeId }?.model ?: "",
                    onItemSelected = { newBike ->
                        viewModel.updateSelectedRide(bikeId = bikes.firstOrNull { it.model == newBike }?.id)
                    }
                )
                TextLabel(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    inputText = stringResource(id = R.string.distance),
                    isRequired = true
                )
                CustomTextField(
                    modifier = Modifier.padding(10.dp),
                    value = (viewModel.selectedRide.collectAsState().value?.distance
                        ?: 0.0).toString(),
                    error = distanceError,
                    displayUnit = true,
                    unit = bikes.firstOrNull { it.id == selectedRide?.bikeId }?.distanceUnit
                        ?: DistanceUnit.KM,
                    onValueChange = { newDistance ->
                        distanceError =
                            if (newDistance.isBlank()) {
                                requiredFieldErrorMessage
                            } else if (newDistance.toDoubleOrNull() == null) {
                                invalidInputErrorMessage
                            } else null

                        if (newDistance.isNotBlank() && newDistance.toDoubleOrNull() != null) {
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
                CustomDateTimePicker(
                    modifier = Modifier.padding(10.dp),
                    selectedTime = selectedRide?.duration ?: 0,
                    onTimeSelected = { newDuration ->
                        viewModel.updateSelectedRide(duration = newDuration)
                    }
                )
                TextLabel(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    inputText = stringResource(id = R.string.date),
                    isRequired = true
                )
                CustomDateTimePicker(
                    modifier = Modifier.padding(10.dp),
                    isDatePicker = true,
                    selectedDate = selectedRide?.date,
                    onDateSelected = { newDate ->
                        viewModel.updateSelectedRide(date = newDate)
                    }
                )
            }
        },
        bottomBar = {
            CustomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                text = stringResource(id = if (selectedRide?.id == null) R.string.add_ride else R.string.save),
                enabled = isInputValid
            ) {
                viewModel.updateSelectedRide()
                viewModel.saveSelectedRide()
                navController.navigate(BottomMenuItem.Rides.route)
            }
        }
    )
}