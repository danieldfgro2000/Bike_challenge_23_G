package com.bikechallenge23g.domain.di

import com.bikechallenge23g.domain.repository.BikeRepository
import com.bikechallenge23g.domain.usecase.GetBikesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideSavedBikes (bikeRepository: BikeRepository) : GetBikesUseCase {
        return GetBikesUseCase(bikeRepository)
    }
}