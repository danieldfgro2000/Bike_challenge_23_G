package com.bikechallenge23g.domain.usecase

import com.bikechallenge23g.domain.repository.BikeRepository

class UpdateDefaultBikeUseCase(private val bikeRepository: BikeRepository) {
    suspend fun execute(bikeId: Int) = bikeRepository.updateDefaultBike(bikeId)
}