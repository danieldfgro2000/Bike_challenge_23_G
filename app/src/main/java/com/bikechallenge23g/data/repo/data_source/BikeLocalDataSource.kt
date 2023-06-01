package com.bikechallenge23g.data.repo.data_source

import com.bikechallenge23g.data.model.Bike
import kotlinx.coroutines.flow.Flow

interface BikeLocalDataSource {

    fun saveBikeToDb(bike: Bike)
    fun getSavedBikes(): Flow<List<Bike>>

    fun deleteBikeFromDb(bike: Bike)
}