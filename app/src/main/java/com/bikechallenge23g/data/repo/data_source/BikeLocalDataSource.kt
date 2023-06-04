package com.bikechallenge23g.data.repo.data_source

import com.bikechallenge23g.data.model.Bike
import kotlinx.coroutines.flow.Flow

interface BikeLocalDataSource {
    suspend fun saveBikeToDb(bike: Bike)
    suspend fun updateDefaultBike(bikeId: Int)
    suspend fun updateServiceReminder(isReminderActive: Boolean, bikeId: Int)
    suspend fun deleteBikeFromDb(bike: Bike)
    suspend fun getSavedBikes(): Flow<List<Bike>>
}