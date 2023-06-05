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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bikechallenge23g.R
import com.bikechallenge23g.presentation.navigation.BottomMenuItem
import com.bikechallenge23g.presentation.ui.composables.CustomButton
import com.bikechallenge23g.presentation.ui.composables.CustomTextField
import com.bikechallenge23g.presentation.ui.composables.DropdownSelector
import com.bikechallenge23g.presentation.ui.composables.TextLabel
import com.bikechallenge23g.presentation.ui.composables.TopBar
import com.bikechallenge23g.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun AddEditRideScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val screenWidth = LocalConfiguration.current.screenHeightDp
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    val emptyFieldErrorMessage = stringResource(id = R.string.required_field)

    val selectedRide = viewModel.selectedRide.collectAsState().value

    val bikes = viewModel.bikes.collectAsState().value

    var isInputValid = false
    selectedRide?.let {
        with(selectedRide) {
            isInputValid = bikeId != null && distance != 0.0 && duration != 0 && date != 0L
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
            ) {
                TextLabel(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    inputText = stringResource(id = R.string.ride_title)
                )
                CustomTextField(
                    modifier = Modifier
                        .padding(10.dp)
                        .onGloballyPositioned {
                            coroutineScope.launch {
                                scrollState.animateScrollTo(screenHeight / 2)
                            }
                        },
                    value = selectedRide?.name ?: "",
                    onValueChange = { newName ->
                        viewModel.updateSelectedRide(name = newName)
                    }
                )
                TextLabel(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    inputText = stringResource(id = R.string.bike)
                )
                DropdownSelector(
                    modifier = Modifier.padding(10.dp),
                    items = bikes.map { it.model ?: "" },
                    selectedItem = bikes.map { it.model ?: "" }.firstOrNull() ?: "",
                    onItemSelected = { newBike ->
                        viewModel.updateSelectedRide(bikeId = bikes.firstOrNull { it.model == newBike }?.id)
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