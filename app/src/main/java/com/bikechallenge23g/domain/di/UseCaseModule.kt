package com.bikechallenge23g.domain.di

import com.bikechallenge23g.domain.repository.BikeRepository
import com.bikechallenge23g.domain.usecase.DeleteBikeUseCase
import com.bikechallenge23g.domain.usecase.GetBikesUseCase
import com.bikechallenge23g.domain.usecase.SaveBikeUseCase
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
    fun provideGetBikesUseCase(bikeRepository: BikeRepository): GetBikesUseCase {
        return GetBikesUseCase(bikeRepository)
    }

    @Singleton
    @Provides
    fun provideSaveBikeUseCase(bikeRepository: BikeRepository): SaveBikeUseCase {
        return SaveBikeUseCase(bikeRepository)
    }

    @Singleton
    @Provides
    fun provideDeleteBikeUseCase(bikeRepository: BikeRepository): DeleteBikeUseCase {
        return DeleteBikeUseCase(bikeRepository)
    }

}