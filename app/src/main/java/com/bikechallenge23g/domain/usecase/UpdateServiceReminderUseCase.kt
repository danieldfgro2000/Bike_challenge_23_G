package com.bikechallenge23g.domain.usecase

import com.bikechallenge23g.domain.repository.BikeRepository

class UpdateServiceReminderUseCase(private val bikeRepository: BikeRepository) {
    suspend fun execute(isReminderActive: Boolean, bikeId: Int) =
        bikeRepository.updateServiceReminder(isReminderActive, bikeId)
}