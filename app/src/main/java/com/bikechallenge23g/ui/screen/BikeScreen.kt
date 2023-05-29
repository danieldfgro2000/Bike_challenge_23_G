package com.bikechallenge23g.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.bikechallenge23g.ui.MainViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BikeScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    Scaffold(modifier = Modifier.fillMaxSize(), content = {
        Text(text = "Bikes")
    })
}