package com.bikechallenge23g.domain.repository

import com.bikechallenge23g.data.model.Bike
import com.bikechallenge23g.data.model.enums.DistanceUnit
import kotlinx.coroutines.flow.Flow

interface BikeRepository {
    suspend fun saveBike(bike: Bike)
    suspend fun updateDefaultBike(bikeId: Int)
    suspend fun updateServiceReminder(isReminderActive: Boolean, bikeId: Int)
    suspend fun updateServiceInterval(bikeId: Int, newInterval: Int)
    suspend fun updateDistanceUnit(bikeId: Int, newUnit: DistanceUnit)
    suspend fun deleteBike(bike: Bike)
    suspend fun getSavedBikes(): Flow<List<Bike>>
}