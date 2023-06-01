package com.bikechallenge23g.presentation.ui.screen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.bikechallenge23g.presentation.viewmodel.MainViewModel

@Composable
fun RideScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    Text(text = "Ride")
}