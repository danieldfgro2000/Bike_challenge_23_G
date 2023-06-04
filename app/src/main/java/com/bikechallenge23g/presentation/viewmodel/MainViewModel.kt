package com.bikechallenge23g.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bikechallenge23g.data.model.Bike
import com.bikechallenge23g.data.model.enums.BikeColors
import com.bikechallenge23g.data.model.enums.BikeTypes
import com.bikechallenge23g.data.model.enums.BikeWheels
import com.bikechallenge23g.data.model.enums.DistanceUnits
import com.bikechallenge23g.domain.usecase.DeleteBikeUseCase
import com.bikechallenge23g.domain.usecase.GetBikesUseCase
import com.bikechallenge23g.domain.usecase.SaveBikeUseCase
import com.bikechallenge23g.domain.usecase.UpdateDefaultBikeUseCase
import com.bikechallenge23g.domain.usecase.UpdateServiceReminderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val application: Application,
    private val saveBikeUseCase: SaveBikeUseCase,
    private val updateDefaultBikeUseCase: UpdateDefaultBikeUseCase,
    private val updateServiceReminderUseCase: UpdateServiceReminderUseCase,
    private val deleteBikeUseCase: DeleteBikeUseCase,
    private val getBikesUseCase: GetBikesUseCase
) : AndroidViewModel(application) {

    init {
        getAllBikes()
    }

    fun getAllBikes() = viewModelScope.launch(IO) {
        getBikesUseCase.execute().collect { bikes ->
            bikes.let {
                Log.e("Bikes= ", "$bikes")
                if (bikes.isNotEmpty()) {
                    _bikes.value = it
                }
            }
        }
    }

    private val _bikes = MutableStateFlow(listOf<Bike>())
    val bikes: StateFlow<List<Bike>>
        get() = _bikes

    private var _newBike = MutableStateFlow(Bike())
    val newBike: StateFlow<Bike>
        get() = _newBike

    fun saveNewBike() = viewModelScope.launch(IO) {
        saveBikeUseCase.execute(newBike.value)
    }

    fun updateNewBike(
        bikeType: BikeTypes = newBike.value.type,
        isDefault: Boolean = newBike.value.isDefault,
        model: String = newBike.value.model,
        bikeColor: BikeColors = newBike.value.bikeColor,
        wheelSize: BikeWheels = newBike.value.wheelSize,
        dueService: String = newBike.value.dueService,
        isServiceReminderActive: Boolean = newBike.value.isServiceReminderActive,
        distance: Double = newBike.value.distance
    ) {
        _newBike.value = Bike(
            id = null,
            bikeType,
            isDefault,
            model,
            bikeColor,
            wheelSize,
            dueService,
            isServiceReminderActive,
            distance
        )
        Log.e("New bike = ", "${_newBike.value}")
    }

    fun updateDefaultBike(bikeId: Int?) {
        Log.e("bikeID", "$bikeId")
        bikeId?.let {
            viewModelScope.launch(IO) {
                updateDefaultBikeUseCase.execute(bikeId)
            }
        }
    }

    fun updateServiceReminder(isReminderActive: Boolean, bikeId: Int?) {
        Log.e("bikeID", "$bikeId")
        bikeId?.let {
            viewModelScope.launch(IO) {
                updateServiceReminderUseCase.execute(isReminderActive, bikeId)
            }
        }
    }

    private val _defaultBike = MutableStateFlow(bikes.value.map { it.type.name }.firstOrNull())
    val defaultBike: MutableStateFlow<String?>
        get() = _defaultBike

    private val _serviceReminder = MutableStateFlow("100km")
    val serviceReminder: StateFlow<String>
        get() = _serviceReminder

    private val _distanceUnit = MutableStateFlow(DistanceUnits.KM)
    val distanceUnit: StateFlow<DistanceUnits>
        get() = _distanceUnit

    fun updateDistanceUnit(newDistanceUnit: DistanceUnits) {
        _distanceUnit.value = newDistanceUnit
    }
}