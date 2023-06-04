package com.bikechallenge23g.domain.di

import com.bikechallenge23g.domain.repository.BikeRepository
import com.bikechallenge23g.domain.usecase.DeleteBikeUseCase
import com.bikechallenge23g.domain.usecase.GetBikesUseCase
import com.bikechallenge23g.domain.usecase.SaveBikeUseCase
import com.bikechallenge23g.domain.usecase.UpdateDefaultBikeUseCase
import com.bikechallenge23g.domain.usecase.UpdateDistanceUnitUseCase
import com.bikechallenge23g.domain.usecase.UpdateServiceIntervalUseCase
import com.bikechallenge23g.domain.usecase.UpdateServiceReminderUseCase
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
    fun provideUpdateDefaultBikeUseCase(bikeRepository: BikeRepository): UpdateDefaultBikeUseCase {
        return UpdateDefaultBikeUseCase(bikeRepository)
    }

    @Singleton
    @Provides
    fun provideUpdateServiceReminderUseCase(bikeRepository: BikeRepository): UpdateServiceReminderUseCase {
        return UpdateServiceReminderUseCase(bikeRepository)
    }

    @Singleton
    @Provides
    fun provideUpdateServiceIntervalUseCase(bikeRepository: BikeRepository): UpdateServiceIntervalUseCase {
        return UpdateServiceIntervalUseCase(bikeRepository)
    }

    @Singleton
    @Provides
    fun provideUpdateDistanceUnitUseCase(bikeRepository: BikeRepository): UpdateDistanceUnitUseCase {
        return UpdateDistanceUnitUseCase(bikeRepository)
    }

    @Singleton
    @Provides
    fun provideDeleteBikeUseCase(bikeRepository: BikeRepository): DeleteBikeUseCase {
        return DeleteBikeUseCase(bikeRepository)
    }
}