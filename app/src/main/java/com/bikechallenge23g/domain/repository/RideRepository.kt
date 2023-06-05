package com.bikechallenge23g.domain.repository

import com.bikechallenge23g.data.model.Ride
import kotlinx.coroutines.flow.Flow

interface RideRepository {

    suspend fun saveRide(ride: Ride)

    suspend fun deleteRide(ride: Ride)

    suspend fun getRides(): Flow<List<Ride>>
}