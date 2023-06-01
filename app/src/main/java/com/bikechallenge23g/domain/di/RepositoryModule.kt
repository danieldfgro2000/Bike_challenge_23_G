package com.bikechallenge23g.domain.di

import com.bikechallenge23g.data.repo.BikeRepositoryImpl
import com.bikechallenge23g.data.repo.data_source.BikeLocalDataSource
import com.bikechallenge23g.domain.repository.BikeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import javax.inject.Singleton

@Module
@InstallIn
class RepositoryModule {

    @Singleton
    @Provides
    fun provideBikeRepository(
        bikeLocalDataSource: BikeLocalDataSource
    ) : BikeRepository = BikeRepositoryImpl(bikeLocalDataSource)
}