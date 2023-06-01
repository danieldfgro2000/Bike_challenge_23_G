package com.bikechallenge23g.data.repo

import com.bikechallenge23g.data.model.Bike
import com.bikechallenge23g.data.repo.data_source.BikeLocalDataSource
import com.bikechallenge23g.domain.repository.BikeRepository
import kotlinx.coroutines.flow.Flow

class BikeRepositoryImpl(
    private val bikeLocalDataSource: BikeLocalDataSource
) : BikeRepository {
    override fun getSavedBikes(): Flow<List<Bike>> = bikeLocalDataSource.getSavedBikes()
}