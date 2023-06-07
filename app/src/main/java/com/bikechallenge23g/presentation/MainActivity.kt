package com.bikechallenge23g.presentation


import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.bikechallenge23g.data.model.Bike
import com.bikechallenge23g.data.notification.AlarmSetter
import com.bikechallenge23g.presentation.ui.BikeApp
import com.bikechallenge23g.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        setContent {
            BikeApp(viewModel)

        }
    }

    override fun onStart() {
        super.onStart()

        val context = this.applicationContext

        var defaultBike: Bike? = null

        lifecycleScope.launchWhenStarted {
            viewModel.defaultBike.collect {
                defaultBike = it
                Log.e("onStart ", "defaultBike = $it")
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.setAlarm.collect { setAlarmOn ->
                if (setAlarmOn) {
                    Log.e("AlarmSetter ", "Set alarm")
                    AlarmSetter(context = context, defaultBike = defaultBike).scheduleAlarm()

                } else {
                    Log.e("AlarmSetter ", "Cancel alarm")
                    AlarmSetter(context).cancelAlarm()
                }
            }
        }
    }
}