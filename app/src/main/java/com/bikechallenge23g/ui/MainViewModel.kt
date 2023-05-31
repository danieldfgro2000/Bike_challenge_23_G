package com.bikechallenge23g.ui

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val _defaultBike = MutableStateFlow("NukeProof")
    val defaultBike: StateFlow<String>
        get() = _defaultBike

    private val _serviceReminder = MutableStateFlow("100km")
    val serviceReminder: StateFlow<String>
        get() = _serviceReminder
}