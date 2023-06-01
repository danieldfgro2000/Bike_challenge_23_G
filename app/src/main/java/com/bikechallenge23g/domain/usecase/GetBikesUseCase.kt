package com.bikechallenge23g.domain.usecase

import com.bikechallenge23g.data.model.Bike
import com.bikechallenge23g.domain.repository.BikeRepository
import kotlinx.coroutines.flow.Flow

class GetBikesUseCase(private val bikeRepository: BikeRepository) {
    fun execute(): Flow<List<Bike>> = bikeRepository.getSavedBikes()
}