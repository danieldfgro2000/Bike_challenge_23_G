package com.bikechallenge23g.domain.di

import android.app.Application
import com.bikechallenge23g.domain.usecase.DeleteBikeUseCase
import com.bikechallenge23g.domain.usecase.GetBikesUseCase
import com.bikechallenge23g.domain.usecase.SaveBikeUseCase
import com.bikechallenge23g.presentation.viewmodel.MainViewModel
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
        saveBikeUseCase: SaveBikeUseCase,
        deleteBikeUseCase: DeleteBikeUseCase,
        getBikesUseCase: GetBikesUseCase
    ): MainViewModelFactory {
        return MainViewModelFactory(
            application,
            getBikesUseCase,
            saveBikeUseCase,
            deleteBikeUseCase
        )
    }

    @Singleton
    @Provides
    fun provideMainViewModel(
        application: Application,
        saveBikeUseCase: SaveBikeUseCase,
        deleteBikeUseCase: DeleteBikeUseCase,
        getBikesUseCase: GetBikesUseCase
    ): MainViewModel {
        return MainViewModel(
            application,
            saveBikeUseCase,
            deleteBikeUseCase,
            getBikesUseCase
        )
    }
}