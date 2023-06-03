package com.bikechallenge23g.domain.usecase

import com.bikechallenge23g.data.model.Bike
import com.bikechallenge23g.domain.repository.BikeRepository

class SaveBikeUseCase(private val bikeRepository: BikeRepository) {
    suspend fun execute(bike: Bike) = bikeRepository.saveBike(bike)
}