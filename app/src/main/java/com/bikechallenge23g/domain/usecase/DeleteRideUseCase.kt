package com.bikechallenge23g.domain.usecase

import com.bikechallenge23g.data.model.Ride
import com.bikechallenge23g.domain.repository.RideRepository

class DeleteRideUseCase(private val rideRepository: RideRepository) {
    suspend fun execute(ride: Ride) = rideRepository.deleteRide(ride)
}