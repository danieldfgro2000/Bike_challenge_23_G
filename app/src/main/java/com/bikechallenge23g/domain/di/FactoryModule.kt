package com.bikechallenge23g.domain.di

import android.app.Application
import com.bikechallenge23g.domain.usecase.GetBikesUseCase
import com.bikechallenge23g.presentation.viewmodel.MainViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Singleton
    @Provides
    fun provideMainViewModelFactory(
        application: Application,
        getBikesUseCase: GetBikesUseCase
    ) : MainViewModelFactory {
        return MainViewModelFactory(
            application,
            getBikesUseCase
        )
    }
}