package com.bikechallenge23g.data.repo.data_source

import com.bikechallenge23g.data.model.Bike
import kotlinx.coroutines.flow.Flow

interface BikeLocalDataSource {
    fun getSavedBikes(): Flow<List<Bike>>
}