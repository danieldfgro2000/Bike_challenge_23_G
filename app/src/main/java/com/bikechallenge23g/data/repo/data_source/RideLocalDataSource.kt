package com.bikechallenge23g.data.repo.data_source

import com.bikechallenge23g.data.model.Ride
import kotlinx.coroutines.flow.Flow

interface RideLocalDataSource {
    suspend fun saveRideToDb(ride: Ride)
    suspend fun deleteRideFromDb(ride: Ride)
    suspend fun getSavedRides(): Flow<List<Ride>>
}