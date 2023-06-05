package com.bikechallenge23g.domain.usecase

import com.bikechallenge23g.data.model.Ride
import com.bikechallenge23g.domain.repository.RideRepository
import kotlinx.coroutines.flow.Flow

class GetRidesUseCase(private val rideRepository: RideRepository) {
    suspend fun execute(): Flow<List<Ride>> = rideRepository.getRides()
}