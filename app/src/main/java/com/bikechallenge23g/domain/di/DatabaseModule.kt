package com.bikechallenge23g.domain.di

import android.app.Application
import androidx.room.Room
import com.bikechallenge23g.data.db.BikeDao
import com.bikechallenge23g.data.db.BikeDatabase
import com.bikechallenge23g.data.db.RideDao
import com.bikechallenge23g.data.repo.data_source.BikeLocalDataSource
import com.bikechallenge23g.data.repo.data_source.RideLocalDataSource
import com.bikechallenge23g.data.repo.data_source_impl.BikeLocalDataSourceImpl
import com.bikechallenge23g.data.repo.data_source_impl.RideLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideBikesDatabase(application: Application) : BikeDatabase {
        return Room.databaseBuilder(application, BikeDatabase::class.java, "bikes_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideBikesDao(bikeDatabase: BikeDatabase): BikeDao {
        return bikeDatabase.getBikeDao()
    }

    @Singleton
    @Provides
    fun provideRideDao(bikeDatabase: BikeDatabase): RideDao {
        return bikeDatabase.getRideDao()
    }

    @Singleton
    @Provides
    fun provideLocalDataSource(bikeDao: BikeDao): BikeLocalDataSource {
        return BikeLocalDataSourceImpl(bikeDao)
    }

    @Singleton
    @Provides
    fun provideRideLocalDataSource(rideDao: RideDao): RideLocalDataSource {
        return RideLocalDataSourceImpl(rideDao)
    }
}