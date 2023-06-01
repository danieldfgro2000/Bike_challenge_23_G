package com.bikechallenge23g.presentation.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bikechallenge23g.R
import com.bikechallenge23g.data.model.enums.DistanceUnits
import com.bikechallenge23g.presentation.ui.composables.CustomSwitch
import com.bikechallenge23g.presentation.ui.composables.DropdownSelector
import com.bikechallenge23g.presentation.ui.composables.TextCard
import com.bikechallenge23g.presentation.ui.composables.TextLabel
import com.bikechallenge23g.presentation.viewmodel.MainViewModel

@Composable
fun SettingScreen(
    navController: NavController,
    viewModel: MainViewModel
) {

    val selectedDistanceUnit = remember { mutableStateOf(DistanceUnits.KM.name) }
    val serviceReminder by viewModel.serviceReminder.collectAsState()
    val defaultBike by viewModel.defaultBike.collectAsState()
    val bikes by viewModel.bikes.collectAsState()
    Scaffold(
        backgroundColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TextLabel(
                inputText = stringResource(id = R.string.settings),
                textStyle = MaterialTheme.typography.titleLarge
            )
        },
        content = { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {

                    TextLabel(inputText = stringResource(id = R.string.distance_units))
                    DropdownSelector(
                        DistanceUnits.values().map { it.name },
                        selectedItem = selectedDistanceUnit.value
                    ) {
                        selectedDistanceUnit.value = it
                    }

                    TextLabel(inputText = stringResource(id = R.string.service_reminder))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        TextCard(serviceReminder, modifier = Modifier.weight(1f))
                        CustomSwitch()
                    }
                    if (bikes.isNotEmpty()) {
                        TextLabel(inputText = stringResource(id = R.string.default_bike))
                        DropdownSelector(
                            bikes.map { it.type.name },
                            selectedItem = selectedDistanceUnit.value
                        ) {
                            selectedDistanceUnit.value = it
                        }
                    }
                }
            }
        }
    )
}

