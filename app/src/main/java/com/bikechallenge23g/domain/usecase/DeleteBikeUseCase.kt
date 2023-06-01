package com.bikechallenge23g.domain.usecase

import com.bikechallenge23g.data.model.Bike
import com.bikechallenge23g.domain.repository.BikeRepository

class DeleteBikeUseCase(private val bikeRepository: BikeRepository) {
    fun execute(bike: Bike) = bikeRepository.deleteBike(bike)
}