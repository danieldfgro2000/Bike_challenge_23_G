package com.bikechallenge23g.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bikechallenge23g.data.model.Bike
import com.bikechallenge23g.data.model.Ride
import com.bikechallenge23g.data.model.enums.BikeColor
import com.bikechallenge23g.data.model.enums.BikeType
import com.bikechallenge23g.data.model.enums.BikeWheel
import com.bikechallenge23g.data.model.enums.DistanceUnit
import com.bikechallenge23g.domain.usecase.CalcChartUseCase
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
import kotlinx.coroutines.Dispatchers.Default
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
    private val calcChartUseCase: CalcChartUseCase
) : AndroidViewModel(application) {

    fun getAllBikes() = viewModelScope.launch(IO) {
        getBikesUseCase.execute().collect { bikes ->
            bikes.let { sBikes ->
                Log.e("getAllBikes(ViewModel):", "$bikes")
                if (bikes.isNotEmpty()) {
                    _bikes.value = sBikes
                    val default = sBikes.firstOrNull { it.isDefault == true }

                    if (default != null) {
                        _defaultBike.value = default
                        setServiceReminder()
                    }
                }
                Log.e("ViewModel efault bike (getAllbikes)=: ", "d ${defaultBike.value}")
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

    fun setServiceReminder() {
        val b = defaultBike.value
        _setAlarm.value =
            b?.isServiceReminderActive == true &&
                    b.serviceIn?.let { due ->
                        b.serviceReminder?.let { reminder ->
                            b.distance?.let { distance ->
                                due - distance < reminder
                            }
                        }
                    } == true
    }

    private var _selectedBike: MutableStateFlow<Bike?> = MutableStateFlow(Bike())
    val selectedBike: MutableStateFlow<Bike?>
        get() = _selectedBike

    fun clearSelectedBike() {
        _selectedBike.value = null
    }

    fun saveSelectedBike(clear: Boolean = true) = viewModelScope.launch(IO) {
        Log.e("Save bike", "${selectedBike.value}")
        selectedBike.value?.let {
            Log.e("Save bike", "$it")
            saveBikeUseCase.execute(it)
        }
        if (clear) {
            clearSelectedBike()
        }
    }

    fun updateBike(
        selected: Boolean? = null,
        default: Boolean? = null,
        bike: Bike? = null,
        id: Int? = null,
        bikeType: BikeType? = null,
        isDefault: Boolean? = null,
        model: String? = null,
        bikeColor: BikeColor? = null,
        wheelSize: BikeWheel? = null,
        serviceIn: Int? = null,
        serviceReminder: Int? = null,
        isServiceReminderActive: Boolean? = null,
        distance: Double? = null,
        distanceUnit: DistanceUnit? = null
    ) {
        selected?.let {
            Log.e("UPDATING", "SELECTED")
            bike?.let { _selectedBike.value = it }
            _selectedBike.value = Bike(
                id = id ?: selectedBike.value?.id,
                type = bikeType ?: selectedBike.value?.type ?: BikeType.MTB,
                isDefault = isDefault ?: selectedBike.value?.isDefault ?: true,
                model = model ?: selectedBike.value?.model,
                bikeColor = bikeColor ?: selectedBike.value?.bikeColor ?: BikeColor.GREEN,
                wheelSize = wheelSize ?: selectedBike.value?.wheelSize ?: BikeWheel.BIG,
                serviceIn = serviceIn ?: selectedBike.value?.serviceIn ?: 100,
                serviceReminder = serviceReminder ?: selectedBike.value?.serviceReminder ?: 100,
                isServiceReminderActive = isServiceReminderActive
                    ?: selectedBike.value?.isServiceReminderActive ?: false,
                distance = distance ?: selectedBike.value?.distance ?: 0.0,
                distanceUnit = distanceUnit ?: selectedBike.value?.distanceUnit ?: DistanceUnit.KM
            )
            Log.e("selected bike = ", "${_selectedBike.value}")
        }
        default?.let {
            Log.e("UPDATING", "DEFAULT")
            bike?.let { _defaultBike.value = it }
            _defaultBike.value = Bike(
                id = id ?: defaultBike.value?.id,
                type = bikeType ?: defaultBike.value?.type,
                isDefault = isDefault ?: defaultBike.value?.isDefault,
                model = model ?: defaultBike.value?.model,
                bikeColor = bikeColor ?: defaultBike.value?.bikeColor,
                wheelSize = wheelSize ?: defaultBike.value?.wheelSize,
                serviceIn = serviceIn ?: defaultBike.value?.serviceIn,
                serviceReminder = serviceReminder ?: defaultBike.value?.serviceReminder,
                isServiceReminderActive = isServiceReminderActive
                    ?: defaultBike.value?.isServiceReminderActive,
                distance = distance ?: defaultBike.value?.distance,
                distanceUnit = distanceUnit ?: defaultBike.value?.distanceUnit
            )
            Log.e("default bike = ", "${_selectedBike.value}")
        }
    }

    fun updateDefaultBike(bikeId: Int?) {
        bikeId?.let {
            viewModelScope.launch(IO) {
                Log.e("update default = ", "${defaultBike.value}")
                updateDefaultBikeUseCase.execute(bikeId)
                getAllBikes()
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
                getAllBikes()
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
                getAllBikes()
            }
        }
    }

    fun updateDistanceUnit(newDistanceUnit: DistanceUnit) {
        defaultBike.value?.id?.let {
            viewModelScope.launch(IO) {
                updateDistanceUnitUseCase.execute(it, newDistanceUnit)
            }
            getAllBikes()
        }
    }

    fun deleteBike(bike: Bike) = viewModelScope.launch(IO) {
        deleteBikeUseCase.execute(bike)
        getAllBikes()
    }

    private val _rides = MutableStateFlow(listOf<Ride>())
    val rides: StateFlow<List<Ride>>
        get() = _rides

    private var _selectedRide: MutableStateFlow<Ride?> = MutableStateFlow(Ride())
    val selectedRide: MutableStateFlow<Ride?>
        get() = _selectedRide

    fun clearSelectedRide() {
        _selectedRide.value = null
    }

    fun updateSelectedRide(
        id: Int? = selectedRide.value?.id,
        name: String? = selectedRide.value?.name,
        bikeId: Int? = selectedRide.value?.bikeId,
        distance: Double? = selectedRide.value?.distance ?: 0.0,
        distanceUnit: DistanceUnit? = selectedRide.value?.distanceUnit ?: DistanceUnit.KM,
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
        Log.e("Updated ride = ", "${_selectedRide.value}")
    }

    fun saveSelectedRide(clear: Boolean = true) = viewModelScope.launch(IO) {
        selectedRide.value?.let { saveRideUseCase.execute(it) }
        getAllRides()
        if (clear) {
            clearSelectedRide()
        }
    }

    fun deleteRide(ride: Ride) = viewModelScope.launch(IO) {
        deleteRideUseCase.execute(ride)
        getAllRides()
    }

    fun getAllRides() = viewModelScope.launch(IO) {
        getRidesUseCase.execute().collect { rides ->
            rides.let {
                Log.e("getAllRides (ViewModel):", "$rides")
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

    private var _chartData: MutableStateFlow<Pair<Double, List<Triple<BikeType, Float, Color>>>> =
        MutableStateFlow(Pair(0.0, listOf()))
    val chartData: MutableStateFlow<Pair<Double, List<Triple<BikeType, Float, Color>>>>
        get() = _chartData

    fun getChartData() {
        viewModelScope.launch(Default) {
            calcChartUseCase.execute(rides.value, bikes.value).collect {
                _chartData.value = it
            }
        }
    }
}