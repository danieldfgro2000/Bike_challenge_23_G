package com.bikechallenge23g.domain.di

import android.app.Application
import com.bikechallenge23g.domain.usecase.DeleteBikeUseCase
import com.bikechallenge23g.domain.usecase.GetBikesUseCase
import com.bikechallenge23g.domain.usecase.SaveBikeUseCase
import com.bikechallenge23g.domain.usecase.UpdateDefaultBikeUseCase
import com.bikechallenge23g.domain.usecase.UpdateDistanceUnitUseCase
import com.bikechallenge23g.domain.usecase.UpdateServiceIntervalUseCase
import com.bikechallenge23g.domain.usecase.UpdateServiceReminderUseCase
import com.bikechallenge23g.presentation.viewmodel.MainViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ViewModelModule {
    @Singleton
    @Provides
    fun provideMainViewModel(
        application: Application,
        saveBikeUseCase: SaveBikeUseCase,
        updateDefaultBikeUseCase: UpdateDefaultBikeUseCase,
        updateServiceReminderUseCase: UpdateServiceReminderUseCase,
        updateServiceIntervalUseCase: UpdateServiceIntervalUseCase,
        updateDistanceUnitUseCase: UpdateDistanceUnitUseCase,
        deleteBikeUseCase: DeleteBikeUseCase,
        getBikesUseCase: GetBikesUseCase
    ): MainViewModel {
        return MainViewModel(
            application,
            saveBikeUseCase,
            updateDefaultBikeUseCase,
            updateServiceReminderUseCase,
            updateServiceIntervalUseCase,
            updateDistanceUnitUseCase,
            deleteBikeUseCase,
            getBikesUseCase
        )
    }
}