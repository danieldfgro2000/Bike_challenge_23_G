package com.bikechallenge23g.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bikechallenge23g.data.model.Bike
import com.bikechallenge23g.data.model.enums.BikeColor
import com.bikechallenge23g.data.model.enums.BikeType
import com.bikechallenge23g.data.model.enums.BikeWheel
import com.bikechallenge23g.data.model.enums.DistanceUnit
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

    private var _newBike: MutableStateFlow<Bike?> = MutableStateFlow(Bike())
    val newBike: MutableStateFlow<Bike?>
        get() = _newBike

    fun saveNewBike() = viewModelScope.launch(IO) {
        newBike.value?.let { saveBikeUseCase.execute(it) }
        _newBike.value = null
    }

    fun updateNewBike(
        bikeType: BikeType? = newBike.value?.type,
        isDefault: Boolean? = newBike.value?.isDefault,
        model: String? = newBike.value?.model,
        bikeColor: BikeColor? = newBike.value?.bikeColor,
        wheelSize: BikeWheel? = newBike.value?.wheelSize,
        serviceIn: Int? = newBike.value?.serviceIn,
        serviceInterval: Int? = newBike.value?.serviceInterval,
        isServiceReminderActive: Boolean? = newBike.value?.isServiceReminderActive,
        distance: Double? = newBike.value?.distance
    ) {
        _newBike.value = Bike(
            id = null,
            bikeType,
            isDefault,
            model,
            bikeColor,
            wheelSize,
            serviceIn,
            serviceInterval,
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

    private val _defaultBike = MutableStateFlow(bikes.value.map { it.type?.name }.firstOrNull())
    val defaultBike: MutableStateFlow<String?>
        get() = _defaultBike

    private val _serviceReminder = MutableStateFlow("100km")
    val serviceReminder: StateFlow<String>
        get() = _serviceReminder

    private val _distanceUnit = MutableStateFlow(DistanceUnit.KM)
    val distanceUnit: StateFlow<DistanceUnit>
        get() = _distanceUnit

    fun updateDistanceUnit(newDistanceUnit: DistanceUnit) {
        _distanceUnit.value = newDistanceUnit
    }

    fun deleteBike(bike: Bike) = viewModelScope.launch(IO) {
        deleteBikeUseCase.execute(bike)
    }
}