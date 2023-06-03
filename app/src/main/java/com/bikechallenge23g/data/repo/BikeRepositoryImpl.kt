package com.bikechallenge23g.data.repo

import com.bikechallenge23g.data.model.Bike
import com.bikechallenge23g.data.repo.data_source.BikeLocalDataSource
import com.bikechallenge23g.domain.repository.BikeRepository
import kotlinx.coroutines.flow.Flow

class BikeRepositoryImpl(
    private val bikeLocalDataSource: BikeLocalDataSource
) : BikeRepository {
    override suspend fun getSavedBikes(): Flow<List<Bike>> = bikeLocalDataSource.getSavedBikes()

    override suspend fun saveBike(bike: Bike) = bikeLocalDataSource.saveBikeToDb(bike)

    override suspend fun deleteBike(bike: Bike) = bikeLocalDataSource.deleteBikeFromDb(bike)
}