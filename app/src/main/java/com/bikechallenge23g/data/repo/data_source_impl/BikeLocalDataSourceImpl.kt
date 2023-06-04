package com.bikechallenge23g.data.repo.data_source_impl

import com.bikechallenge23g.data.db.BikeDao
import com.bikechallenge23g.data.model.Bike
import com.bikechallenge23g.data.model.enums.DistanceUnit
import com.bikechallenge23g.data.repo.data_source.BikeLocalDataSource
import kotlinx.coroutines.flow.Flow

class BikeLocalDataSourceImpl(
    private val bikeDao: BikeDao
) : BikeLocalDataSource {

    override suspend fun saveBikeToDb(bike: Bike) = bikeDao.saveBike(bike)

    override suspend fun updateDefaultBike(bikeId: Int) = bikeDao.updateDefaultBike(bikeId)

    override suspend fun updateServiceReminder(isReminderActive: Boolean, bikeId: Int) =
        bikeDao.updateServiceReminder(isReminderActive = isReminderActive, bikeId = bikeId)

    override suspend fun updateServiceInterval(bikeId: Int, newInterval: Int) =
        bikeDao.updateServiceInterval(bikeId, newInterval)

    override suspend fun updateDistanceUnit(bikeId: Int, newUnit: DistanceUnit) =
        bikeDao.updateDistanceUnit(bikeId, newUnit)

    override suspend fun deleteBikeFromDb(bike: Bike) = bikeDao.deleteBike(bike)

    override suspend fun getSavedBikes(): Flow<List<Bike>> = bikeDao.getAllBikes()
}