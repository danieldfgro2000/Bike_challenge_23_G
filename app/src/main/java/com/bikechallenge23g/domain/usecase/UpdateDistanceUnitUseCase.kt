package com.bikechallenge23g.domain.usecase

import com.bikechallenge23g.data.model.enums.DistanceUnit
import com.bikechallenge23g.domain.repository.BikeRepository

class UpdateDistanceUnitUseCase(private val bikeRepository: BikeRepository) {
    suspend fun execute(bikeId: Int, distanceUnit: DistanceUnit) =
        bikeRepository.updateDistanceUnit(bikeId, distanceUnit)
}