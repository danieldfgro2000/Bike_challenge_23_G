package com.bikechallenge23g.data.repo.data_source

import com.bikechallenge23g.data.model.Bike
import com.bikechallenge23g.data.model.enums.DistanceUnit
import kotlinx.coroutines.flow.Flow

interface BikeLocalDataSource {
    suspend fun saveBikeToDb(bike: Bike)
    suspend fun updateDefaultBike(bikeId: Int)
    suspend fun updateServiceReminder(isReminderActive: Boolean, bikeId: Int)
    suspend fun updateServiceInterval(bikeId: Int, newInterval: Int)
    suspend fun updateDistanceUnit(bikeId: Int, newUnit: DistanceUnit)
    suspend fun deleteBikeFromDb(bike: Bike)
    suspend fun getSavedBikes(): Flow<List<Bike>>
}