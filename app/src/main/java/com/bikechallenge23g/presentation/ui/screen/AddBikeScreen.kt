package com.bikechallenge23g.presentation.ui.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bikechallenge23g.R
import com.bikechallenge23g.data.model.enums.BikeColors
import com.bikechallenge23g.data.model.enums.BikeTypes
import com.bikechallenge23g.data.model.enums.BikeWheels
import com.bikechallenge23g.presentation.ui.composables.ColorRow
import com.bikechallenge23g.presentation.ui.composables.CustomSwitch
import com.bikechallenge23g.presentation.ui.composables.DropdownSelector
import com.bikechallenge23g.presentation.ui.composables.SelectBikePager
import com.bikechallenge23g.presentation.ui.composables.TextCard
import com.bikechallenge23g.presentation.ui.composables.TextLabel
import com.bikechallenge23g.presentation.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddBikeScreen(
    navController: NavController,
    viewModel: MainViewModel
) {

    val bikes by viewModel.bikes.collectAsState()
    var bikeColor by remember { mutableStateOf(BikeColors.BLUE) }

    var bikeWheel by remember { mutableStateOf(BikeWheels.BIG) }
    var bikeType by remember { mutableStateOf(BikeTypes.ELECTRIC) }
    var bikeName by remember { mutableStateOf("") }
    var isDefaultBike by remember { mutableStateOf(true) }

    Scaffold(
        backgroundColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                TextLabel(
                    inputText = stringResource(id = R.string.add_bike),
                    textStyle = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    modifier = Modifier.clickable { navController.popBackStack() },
                    painter = painterResource(id = R.drawable.icon_x),
                    contentDescription = stringResource(
                        id = R.string.close
                    )
                )
            }
        }
    ) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            ColorRow { selectedColor -> bikeColor = selectedColor }
            SelectBikePager(
                bikeTypes = BikeTypes.values(),
                wheels = bikeWheel,
                bikeColors = bikeColor,
                defaultBikeType = bikeType
            ) { bikeTypes -> bikeType = bikeTypes }

            TextLabel(inputText = stringResource(id = R.string.bike_name), isRequired = true)
//            TextField(
//                modifier = Modifier,
//                onValueChange = {Log.e("text", "$it")},
//                value = bikeName,
////                colors = TextFieldDefaults.textFieldColors(
////                    containerColor = MaterialTheme.colorScheme.background,
////                ),
//                textColor = MaterialTheme.colorScheme.secondary,
//                textStyle = MaterialTheme.typography.labelMedium,
//                singleLine = true,
//            )
            Log.e("bike name = ", bikeName)
            TextLabel(inputText = stringResource(id = R.string.wheel_size), isRequired = true)
            DropdownSelector(
                items = BikeWheels.values().map { wheel -> wheel.size },
                selectedItem = bikeWheel.size,
                onItemSelected = { selectedWheel ->
                    bikeWheel =
                        BikeWheels.values().find { available -> available.size == selectedWheel }!!

                    Log.e("Selected wheel = ", bikeWheel.name)
                }
            )
            TextLabel(inputText = stringResource(id = R.string.service_interval), isRequired = true)
            TextCard(text = stringResource(id = R.string.service_interval))

            Row(modifier = Modifier.fillMaxWidth()) {
                TextLabel(inputText = stringResource(id = R.string.default_bike))
                CustomSwitch { changeDefault -> isDefaultBike = changeDefault }
            }
        }
    }
}