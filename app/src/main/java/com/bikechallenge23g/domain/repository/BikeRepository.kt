package com.bikechallenge23g.domain.repository

import com.bikechallenge23g.data.model.Bike
import kotlinx.coroutines.flow.Flow

interface BikeRepository {
    fun getSavedBikes(): Flow<List<Bike>>
    fun saveBike(bike: Bike)
    fun deleteBike(bike: Bike)
}