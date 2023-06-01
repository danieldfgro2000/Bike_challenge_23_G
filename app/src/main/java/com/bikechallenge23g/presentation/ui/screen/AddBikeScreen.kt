package com.bikechallenge23g.presentation.ui.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.bikechallenge23g.presentation.ui.composables.SelectBike
import com.bikechallenge23g.presentation.ui.composables.TextLabel
import com.bikechallenge23g.presentation.viewmodel.MainViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddBikeScreen(
    navController: NavController,
    viewModel: MainViewModel
) {

    val bikes by viewModel.bikes.collectAsState()
    val bikeColor by remember { mutableStateOf(BikeColors.BLUE) }

    val bikeWheels by remember { mutableStateOf(BikeWheels.BIG) }

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
            ColorRow { selectedColor -> Log.e("Selected color", "$selectedColor") }
            SelectBike(bikeTypes = BikeTypes.values(), wheels = bikeWheels, bikeColors = bikeColor)
        }
    }
}