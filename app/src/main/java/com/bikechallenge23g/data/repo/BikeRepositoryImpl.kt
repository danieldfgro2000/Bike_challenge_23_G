package com.bikechallenge23g.data.repo

import com.bikechallenge23g.data.model.Bike
import com.bikechallenge23g.data.model.enums.DistanceUnit
import com.bikechallenge23g.data.repo.data_source.BikeLocalDataSource
import com.bikechallenge23g.domain.repository.BikeRepository
import kotlinx.coroutines.flow.Flow

class BikeRepositoryImpl(
    private val bikeLocalDataSource: BikeLocalDataSource
) : BikeRepository {
    override suspend fun saveBike(bike: Bike) = bikeLocalDataSource.saveBikeToDb(bike)
    override suspend fun updateDefaultBike(bikeId: Int) =
        bikeLocalDataSource.updateDefaultBike(bikeId)

    override suspend fun updateServiceReminderActive(isReminderActive: Boolean, bikeId: Int) =
        bikeLocalDataSource.updateServiceReminderActive(isReminderActive, bikeId)

    override suspend fun updateServiceReminderInterval(bikeId: Int, newInterval: Int) =
        bikeLocalDataSource.updateServiceReminderInterval(bikeId, newInterval)

    override suspend fun updateDistanceUnit(bikeId: Int, newUnit: DistanceUnit) =
        bikeLocalDataSource.updateDistanceUnit(bikeId, newUnit)

    override suspend fun deleteBike(bike: Bike) = bikeLocalDataSource.deleteBikeFromDb(bike)

    override suspend fun getSavedBikes(): Flow<List<Bike>> = bikeLocalDataSource.getSavedBikes()

}