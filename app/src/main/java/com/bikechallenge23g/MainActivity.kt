package com.bikechallenge23g


import android.content.res.Resources.Theme
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.bikechallenge23g.ui.BikeApp
import com.bikechallenge23g.ui.MainViewModel
import com.bikechallenge23g.ui.theme.BikeChallenge23GTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        window.statusBarColor = Color.BLACK
        setContent {
            BikeApp(viewModel)
        }
    }
}