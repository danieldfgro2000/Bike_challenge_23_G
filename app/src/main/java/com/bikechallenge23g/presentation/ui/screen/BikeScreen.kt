package com.bikechallenge23g.presentation.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.bikechallenge23g.presentation.ui.composables.NoBikePlaceholder
import com.bikechallenge23g.presentation.viewmodel.MainViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BikeScreen (
    navController: NavController,
    viewModel: MainViewModel
) {

    val bikes by viewModel.bikes.collectAsState()

    Scaffold(
        backgroundColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize(),
        content = {

            if (bikes.isEmpty()) {
                NoBikePlaceholder(navController)
            }
        })
}