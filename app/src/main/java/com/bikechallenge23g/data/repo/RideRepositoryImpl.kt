package com.bikechallenge23g.data.repo

import com.bikechallenge23g.data.model.Ride
import com.bikechallenge23g.data.repo.data_source.RideLocalDataSource
import com.bikechallenge23g.domain.repository.RideRepository
import kotlinx.coroutines.flow.Flow

class RideRepositoryImpl(
    private val rideLocalDataSource: RideLocalDataSource
) : RideRepository {

    override suspend fun saveRide(ride: Ride) = rideLocalDataSource.saveRideToDb(ride)

    override suspend fun deleteRide(ride: Ride) = rideLocalDataSource.deleteRideFromDb(ride)

    override suspend fun getRides(): Flow<List<Ride>> = rideLocalDataSource.getSavedRides()

}