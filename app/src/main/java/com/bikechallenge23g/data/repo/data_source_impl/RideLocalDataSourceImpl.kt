package com.bikechallenge23g.data.repo.data_source_impl

import com.bikechallenge23g.data.db.RideDao
import com.bikechallenge23g.data.model.Ride
import com.bikechallenge23g.data.repo.data_source.RideLocalDataSource
import kotlinx.coroutines.flow.Flow

class RideLocalDataSourceImpl(
    private val rideDao: RideDao
) : RideLocalDataSource {

    override suspend fun saveRideToDb(ride: Ride) = rideDao.saveRide(ride)

    override suspend fun deleteRideFromDb(ride: Ride) = rideDao.deleteRide(ride)

    override suspend fun getSavedRides(): Flow<List<Ride>> = rideDao.getRides()
}