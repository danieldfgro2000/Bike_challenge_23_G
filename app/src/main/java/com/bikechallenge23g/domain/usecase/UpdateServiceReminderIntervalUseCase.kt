package com.bikechallenge23g.domain.usecase

import com.bikechallenge23g.domain.repository.BikeRepository

class UpdateServiceReminderIntervalUseCase(private val bikeRepository: BikeRepository) {
    suspend fun execute(bikeId: Int, newInterval: Int) =
        bikeRepository.updateServiceReminderInterval(bikeId, newInterval)
}