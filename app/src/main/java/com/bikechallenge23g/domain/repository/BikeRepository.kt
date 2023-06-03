package com.bikechallenge23g.domain.repository

import com.bikechallenge23g.data.model.Bike
import kotlinx.coroutines.flow.Flow

interface BikeRepository {
    suspend fun getSavedBikes(): Flow<List<Bike>>
    suspend fun saveBike(bike: Bike)
    suspend fun deleteBike(bike: Bike)
}