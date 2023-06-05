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
import com.bikechallenge23g.domain.usecase.GetBikesUseCase
import com.bikechallenge23g.domain.usecase.SaveBikeUseCase
import com.bikechallenge23g.domain.usecase.UpdateDefaultBikeUseCase
import com.bikechallenge23g.domain.usecase.UpdateDistanceUnitUseCase
import com.bikechallenge23g.domain.usecase.UpdateServiceIntervalUseCase
import com.bikechallenge23g.domain.usecase.UpdateServiceReminderUseCase
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
    private val updateServiceReminderUseCase: UpdateServiceReminderUseCase,
    private val updateServiceIntervalUseCase: UpdateServiceIntervalUseCase,
    private val updateDistanceUnitUseCase: UpdateDistanceUnitUseCase,
    private val deleteBikeUseCase: DeleteBikeUseCase,
    private val getBikesUseCase: GetBikesUseCase
) : AndroidViewModel(application) {


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

    private var _selectedBike: MutableStateFlow<Bike?> = MutableStateFlow(Bike())
    val selectedBike: MutableStateFlow<Bike?>
        get() = _selectedBike

    fun saveSelectedBike() = viewModelScope.launch(IO) {
        selectedBike.value?.let { saveBikeUseCase.execute(it) }
        _selectedBike.value = null
    }

    fun updateSelectedBike(
        id: Int? = selectedBike.value?.id,
        bikeType: BikeType? = selectedBike.value?.type,
        isDefault: Boolean? = selectedBike.value?.isDefault,
        model: String? = selectedBike.value?.model,
        bikeColor: BikeColor? = selectedBike.value?.bikeColor,
        wheelSize: BikeWheel? = selectedBike.value?.wheelSize,
        serviceIn: Int? = selectedBike.value?.serviceIn,
        serviceInterval: Int? = selectedBike.value?.serviceInterval,
        isServiceReminderActive: Boolean? = selectedBike.value?.isServiceReminderActive,
        distance: Double? = selectedBike.value?.distance
    ) {
        _selectedBike.value = Bike(
            id,
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
        Log.e("New bike = ", "${_selectedBike.value}")
    }

    fun updateDefaultBike(bikeId: Int?) {
        bikeId?.let {
            viewModelScope.launch(IO) {
                updateDefaultBikeUseCase.execute(bikeId)
            }
        }
    }

    fun setSelectedBike(bike: Bike) {
        _selectedBike.value = bike
        Log.e("Selected = ", "${selectedBike.value}")
    }

    fun updateServiceReminder(isReminderActive: Boolean) {
        selectedBike.value?.id?.let {
            viewModelScope.launch(IO) {
                Log.e("bikeID", "$it")
                updateServiceReminderUseCase.execute(isReminderActive, it)
            }
        }
    }

    fun updateServiceInterval() {
        selectedBike.value?.id?.let {
            viewModelScope.launch(IO) {
                Log.e("Updating", "Service interval")
                updateServiceIntervalUseCase.execute(it, selectedBike.value?.serviceInterval ?: 100)
            }
        }
    }

    fun updateDistanceUnit(newDistanceUnit: DistanceUnit) {
        selectedBike.value?.id?.let {
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
        duration: Int? = selectedRide.value?.duration,
        date: Long? = selectedRide.value?.date
    ) {
        _selectedRide.value = Ride(
            id,
            name,
            bikeId,
            distance,
            duration,
            date
        )
        Log.e("New ride = ", "${_selectedRide.value}")
    }

    fun saveSelectedRide() {

    }

}