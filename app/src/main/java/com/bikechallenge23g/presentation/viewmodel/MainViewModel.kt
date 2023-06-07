package com.bikechallenge23g.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bikechallenge23g.data.model.Bike
import com.bikechallenge23g.data.model.Ride
import com.bikechallenge23g.data.model.enums.BikeColor
import com.bikechallenge23g.data.model.enums.BikeType
import com.bikechallenge23g.data.model.enums.BikeWheel
import com.bikechallenge23g.data.model.enums.DistanceUnit
import com.bikechallenge23g.domain.usecase.DeleteBikeUseCase
import com.bikechallenge23g.domain.usecase.DeleteRideUseCase
import com.bikechallenge23g.domain.usecase.GetBikesUseCase
import com.bikechallenge23g.domain.usecase.GetRidesUseCase
import com.bikechallenge23g.domain.usecase.SaveBikeUseCase
import com.bikechallenge23g.domain.usecase.SaveRideUseCase
import com.bikechallenge23g.domain.usecase.UpdateDefaultBikeUseCase
import com.bikechallenge23g.domain.usecase.UpdateDistanceUnitUseCase
import com.bikechallenge23g.domain.usecase.UpdateServiceReminderActiveUseCase
import com.bikechallenge23g.domain.usecase.UpdateServiceReminderIntervalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    private val saveBikeUseCase: SaveBikeUseCase,
    private val updateDefaultBikeUseCase: UpdateDefaultBikeUseCase,
    private val updateServiceReminderActiveUseCase: UpdateServiceReminderActiveUseCase,
    private val updateServiceReminderIntervalUseCase: UpdateServiceReminderIntervalUseCase,
    private val updateDistanceUnitUseCase: UpdateDistanceUnitUseCase,
    private val deleteBikeUseCase: DeleteBikeUseCase,
    private val getBikesUseCase: GetBikesUseCase,
    private val saveRideUseCase: SaveRideUseCase,
    private val getRidesUseCase: GetRidesUseCase,
    private val deleteRideUseCase: DeleteRideUseCase,
) : AndroidViewModel(application) {

    fun getAllBikes() = viewModelScope.launch(IO) {
        getBikesUseCase.execute().collect { bikes ->
            bikes.let { sBikes ->
                Log.e("Bikes= ", "$bikes")
                if (bikes.isNotEmpty()) {
                    _bikes.value = sBikes
                    val default = sBikes.firstOrNull { it.isDefault == true }

                    if (default != null) {
                        _defaultBike.value = default
                        setServiceReminder()
                    }
                }
                Log.e("default bike= ", "${defaultBike.value}")
            }
        }
    }

    private val _bikes = MutableStateFlow(listOf<Bike>())
    val bikes: StateFlow<List<Bike>>
        get() = _bikes


    private val _defaultBike: MutableStateFlow<Bike?> = MutableStateFlow(null)
    val defaultBike: StateFlow<Bike?>
        get() = _defaultBike

    private val _setAlarm: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val setAlarm: StateFlow<Boolean>
        get() = _setAlarm

    private fun setServiceReminder() {
//        Log.e("SetServiceReminder", "Set")
        defaultBike.value?.let { b ->
//            Log.e("Default bike = ", "$b")
//            Log.e("SetServiceReminder", "Bike not null")
            when (b.isServiceReminderActive) {
                true -> {
//                    Log.e("SetServiceReminder", "Reminder active - checking km...")
                    b.serviceIn?.let { due ->
                        b.serviceReminder?.let { reminder ->
                            if (due < reminder) {
//                                Log.e("SetServiceReminder", "Setting the alarm")
                                _setAlarm.value = true
                            }
                        }
                    }
//                    Log.e("setAlarm = ", "${setAlarm.value}")
                }

                false -> {
//                    Log.e("SetServiceReminder", "Reminder inactive - disabling the alarm")
                    _setAlarm.value = false
//                    Log.e("setAlarm = ", "${setAlarm.value}")
                }

                null -> {}
            }
        }
    }

    private var _selectedBike: MutableStateFlow<Bike?> = MutableStateFlow(Bike())
    val selectedBike: MutableStateFlow<Bike?>
        get() = _selectedBike

    fun saveSelectedBike() = viewModelScope.launch(IO) {
        selectedBike.value?.let { saveBikeUseCase.execute(it) }
        _selectedBike.value = null
    }

    fun updateBike(
        selected: Boolean? = null,
        default: Boolean? = null,
        id: Int? = null,
        bikeType: BikeType? = null,
        isDefault: Boolean? = null,
        model: String? = null,
        bikeColor: BikeColor? = null,
        wheelSize: BikeWheel? = null,
        serviceIn: Int? = null,
        serviceReminder: Int? = null,
        serviceInterval: Int? = null,
        isServiceReminderActive: Boolean? = null,
        distance: Double? = null,
    ) {
        selected?.let {
            Log.e("UPDATING", "SELECTED")
            _selectedBike.value = Bike(
                id = id ?: selectedBike.value?.id,
                type = bikeType ?: selectedBike.value?.type,
                isDefault = isDefault ?: selectedBike.value?.isDefault,
                model = model ?: selectedBike.value?.model,
                bikeColor = bikeColor ?: selectedBike.value?.bikeColor,
                wheelSize = wheelSize ?: selectedBike.value?.wheelSize,
                serviceIn = serviceIn ?: selectedBike.value?.serviceIn,
                serviceReminder = serviceReminder ?: selectedBike.value?.serviceReminder,
                serviceInterval = serviceInterval ?: selectedBike.value?.serviceInterval,
                isServiceReminderActive = isServiceReminderActive
                    ?: selectedBike.value?.isServiceReminderActive,
                distance = distance ?: selectedBike.value?.distance
            )
        }
        default?.let {
            Log.e("UPDATING", "DEFAULT")
            _defaultBike.value = Bike(
                id = id ?: defaultBike.value?.id,
                type = bikeType ?: defaultBike.value?.type,
                isDefault = isDefault ?: defaultBike.value?.isDefault,
                model = model ?: defaultBike.value?.model,
                bikeColor = bikeColor ?: defaultBike.value?.bikeColor,
                wheelSize = wheelSize ?: defaultBike.value?.wheelSize,
                serviceIn = serviceIn ?: defaultBike.value?.serviceIn,
                serviceReminder = serviceReminder ?: defaultBike.value?.serviceReminder,
                serviceInterval = serviceInterval ?: defaultBike.value?.serviceInterval,
                isServiceReminderActive = isServiceReminderActive
                    ?: defaultBike.value?.isServiceReminderActive,
                distance = distance ?: defaultBike.value?.distance
            )
        }

        Log.e("selected bike = ", "${_selectedBike.value}")
        Log.e("default bike = ", "${_selectedBike.value}")
    }

    fun updateDefaultBike(bikeId: Int?) {
        bikeId?.let {
            viewModelScope.launch(IO) {
                Log.e("update default = ", "${defaultBike.value}")
                updateDefaultBikeUseCase.execute(bikeId)
            }
        }
    }

    fun setSelectedBike(bike: Bike) {
        _selectedBike.value = bike
        Log.e("Selected = ", "${selectedBike.value}")
    }

    fun updateServiceReminderActive(isReminderActive: Boolean) {
        defaultBike.value?.id?.let {
            viewModelScope.launch(IO) {
                Log.e("bikeID", "$it")
                updateServiceReminderActiveUseCase.execute(isReminderActive, it)
            }
        }
    }

    fun updateServiceReminderInterval() {
        defaultBike.value?.id?.let {
            viewModelScope.launch(IO) {
                Log.e("Updating", "Service interval = ")
                updateServiceReminderIntervalUseCase.execute(
                    it,
                    defaultBike.value?.serviceReminder ?: 100
                )
            }
        }
    }

    fun updateDistanceUnit(newDistanceUnit: DistanceUnit) {
        defaultBike.value?.id?.let {
            viewModelScope.launch(IO) {
                updateDistanceUnitUseCase.execute(it, newDistanceUnit)
            }
        }
    }

    fun deleteBike(bike: Bike) = viewModelScope.launch(IO) {
        deleteBikeUseCase.execute(bike)
    }

    private val _rides = MutableStateFlow(listOf<Ride>())
    val rides: StateFlow<List<Ride>>
        get() = _rides

    private var _selectedRide: MutableStateFlow<Ride?> = MutableStateFlow(Ride())
    val selectedRide: MutableStateFlow<Ride?>
        get() = _selectedRide

    fun updateSelectedRide(
        id: Int? = selectedRide.value?.id,
        name: String? = selectedRide.value?.name,
        bikeId: Int? = selectedRide.value?.bikeId,
        distance: Double? = selectedRide.value?.distance,
        distanceUnit: DistanceUnit? = selectedRide.value?.distanceUnit,
        duration: Int? = selectedRide.value?.duration,
        date: Long? = selectedRide.value?.date
    ) {
        _selectedRide.value = Ride(
            id,
            name,
            bikeId,
            distance,
            distanceUnit,
            duration,
            date
        )
        Log.e("ride = ", "${_selectedRide.value}")
    }

    fun saveSelectedRide() = viewModelScope.launch(IO) {
        selectedRide.value?.let { saveRideUseCase.execute(it) }
        _selectedRide.value = null
    }

    fun deleteRide(ride: Ride) = viewModelScope.launch(IO) {
        deleteRideUseCase.execute(ride)
    }

    fun getAllRides() = viewModelScope.launch(IO) {
        getRidesUseCase.execute().collect { rides ->
            rides.let {
                Log.e("rides= ", "$rides")
                if (rides.isNotEmpty()) {
                    _rides.value = it
                }
            }
        }
    }

    fun setSelectedRide(ride: Ride) {
        _selectedRide.value = ride
        Log.e("Selected = ", "${selectedRide.value}")
    }

}