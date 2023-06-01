package com.bikechallenge23g.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.bikechallenge23g.data.model.Bike
import com.bikechallenge23g.domain.usecase.GetBikesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    getBikesUseCase: GetBikesUseCase
): AndroidViewModel(application) {
    private val _bikes = MutableStateFlow(listOf<Bike>())
    val bikes: StateFlow<List<Bike>>
        get() = _bikes

    private val _defaultBike = MutableStateFlow(bikes.value.map { it.type.name }.firstOrNull())
    val defaultBike: MutableStateFlow<String?>
        get() = _defaultBike

    private val _serviceReminder = MutableStateFlow("100km")
    val serviceReminder: StateFlow<String>
        get() = _serviceReminder
}