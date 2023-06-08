package com.bikechallenge23g.presentation.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bikechallenge23g.R
import com.bikechallenge23g.data.model.enums.DistanceUnit
import com.bikechallenge23g.data.notification.AlarmSetter
import com.bikechallenge23g.presentation.ui.composables.CustomSwitch
import com.bikechallenge23g.presentation.ui.composables.CustomTextField
import com.bikechallenge23g.presentation.ui.composables.DropdownSelector
import com.bikechallenge23g.presentation.ui.composables.TextLabel
import com.bikechallenge23g.presentation.ui.composables.TopBar
import com.bikechallenge23g.presentation.viewmodel.MainViewModel

@Composable
fun SettingScreen(
    viewModel: MainViewModel
) {
    val scrollState = rememberScrollState()
    val bikes = viewModel.bikes.collectAsState().value
    val defaultBike by viewModel.defaultBike.collectAsState()

    val emptyFieldErrorMessage = stringResource(id = R.string.required_field)
    val numberErrorMessage = stringResource(id = R.string.number_input_error)
    var bikeServiceIntervalError by remember { mutableStateOf<String?>(null) }

    val setAlarm by viewModel.setAlarm.collectAsState()
    val context = LocalContext.current

    if (setAlarm) {
        AlarmSetter(context, defaultBike).scheduleAlarm()
    } else {
        AlarmSetter(context).cancelAlarm()
    }

    Scaffold(
        backgroundColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBar(title = R.string.settings) {} },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
                    .verticalScroll(scrollState)
            ) {
                TextLabel(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    inputText = stringResource(id = R.string.distance_units),
                    isRequired = true
                )
                DropdownSelector(
                    modifier = Modifier.padding(10.dp),
                    items = DistanceUnit.values().map { it.name },
                    selectedItem = defaultBike?.distanceUnit?.unit ?: DistanceUnit.KM.unit
                ) {
                    viewModel.updateBike(default = true, distanceUnit = DistanceUnit.valueOf(it))
                    viewModel.updateDistanceUnit(DistanceUnit.valueOf(it))
                }
                TextLabel(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    inputText = stringResource(id = R.string.service_reminder)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    CustomTextField(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        value = (defaultBike?.serviceReminder ?: 100).toString(),
                        error = bikeServiceIntervalError,
                        unit = defaultBike?.distanceUnit ?: DistanceUnit.KM,
                        displayUnit = true
                    ) { newServiceReminderInterval ->
                        bikeServiceIntervalError =
                            if (newServiceReminderInterval.isBlank()) {
                                emptyFieldErrorMessage
                            } else if (newServiceReminderInterval.toIntOrNull() == null) {
                                numberErrorMessage
                            } else null
                        if (newServiceReminderInterval.toIntOrNull() != null) {
                            viewModel.updateBike(
                                default = true,
                                serviceReminder = newServiceReminderInterval.toInt()
                            )
                            viewModel.updateServiceReminderInterval()
                            viewModel.getAllBikes()
                        }
                    }
                    CustomSwitch(
                        defaultState = defaultBike?.isServiceReminderActive ?: false
                    ) { isReminderActive ->
                        viewModel.updateBike(
                            default = true,
                            isServiceReminderActive = isReminderActive
                        )
                        viewModel.setServiceReminder()
                        viewModel.updateServiceReminderActive(isReminderActive)
                    }
                }
                if (bikes.isNotEmpty()) {
                    TextLabel(
                        modifier = Modifier.padding(horizontal = 5.dp),
                        inputText = stringResource(id = R.string.default_bike),
                        isRequired = true
                    )
                    DropdownSelector(
                        modifier = Modifier.padding(10.dp),
                        items = bikes.map { name -> name.model ?: "" },
                        selectedItem = defaultBike?.model ?: ""
                    ) { selectedModel ->
                        val newSelectedBike = bikes.firstOrNull { it.model == selectedModel }
                        newSelectedBike?.let {
                            viewModel.updateDefaultBike(newSelectedBike.id)
                        }
                    }
                }
            }
        }
    )
}

