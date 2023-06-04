package com.bikechallenge23g.domain.usecase

import com.bikechallenge23g.domain.repository.BikeRepository

class UpdateServiceIntervalUseCase(private val bikeRepository: BikeRepository) {
    suspend fun execute(bikeId: Int, newInterval: Int) =
        bikeRepository.updateServiceInterval(bikeId, newInterval)
}