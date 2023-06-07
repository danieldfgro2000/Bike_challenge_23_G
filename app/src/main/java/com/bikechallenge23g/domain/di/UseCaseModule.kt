package com.bikechallenge23g.domain.di

import com.bikechallenge23g.domain.repository.BikeRepository
import com.bikechallenge23g.domain.repository.RideRepository
import com.bikechallenge23g.domain.usecase.DeleteBikeUseCase
import com.bikechallenge23g.domain.usecase.DeleteRideUseCase
import com.bikechallenge23g.domain.usecase.GetBikesUseCase
import com.bikechallenge23g.domain.usecase.GetRidesUseCase
import com.bikechallenge23g.domain.usecase.SaveBikeUseCase
import com.bikechallenge23g.domain.usecase.SaveRideUseCase
import com.bikechallenge23g.domain.usecase.UpdateDefaultBikeUseCase
import com.bikechallenge23g.domain.usecase.UpdateDistanceUnitUseCase
import com.bikechallenge23g.domain.usecase.UpdateServiceReminderActiveUseCase
import com.bikechallenge23g.domain.usecase.UpdateServiceReminderIntervalUseCase
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
    fun provideUpdateServiceReminderActiveUseCase(bikeRepository: BikeRepository): UpdateServiceReminderActiveUseCase {
        return UpdateServiceReminderActiveUseCase(bikeRepository)
    }

    @Singleton
    @Provides
    fun provideUpdateServiceReminderIntervalUseCase(bikeRepository: BikeRepository): UpdateServiceReminderIntervalUseCase {
        return UpdateServiceReminderIntervalUseCase(bikeRepository)
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

    @Singleton
    @Provides
    fun provideSaveRideUseCase(rideRepository: RideRepository): SaveRideUseCase {
        return SaveRideUseCase(rideRepository)
    }

    @Singleton
    @Provides
    fun provideGetRidesUseCase(rideRepository: RideRepository): GetRidesUseCase {
        return GetRidesUseCase(rideRepository)
    }

    @Singleton
    @Provides
    fun provideDeleteRideUseCase(rideRepository: RideRepository): DeleteRideUseCase {
        return DeleteRideUseCase(rideRepository)
    }
}