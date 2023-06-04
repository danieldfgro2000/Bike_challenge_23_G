package com.bikechallenge23g.domain.repository

import com.bikechallenge23g.data.model.Bike
import kotlinx.coroutines.flow.Flow

interface BikeRepository {
    suspend fun saveBike(bike: Bike)
    suspend fun updateDefaultBike(bikeId: Int)
    suspend fun updateServiceReminder(isReminderActive: Boolean, bikeId: Int)
    suspend fun deleteBike(bike: Bike)
    suspend fun getSavedBikes(): Flow<List<Bike>>
}