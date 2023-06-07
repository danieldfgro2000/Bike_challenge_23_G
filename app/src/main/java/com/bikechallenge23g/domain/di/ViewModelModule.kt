package com.bikechallenge23g.domain.di

import android.app.Application
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
        updateServiceReminderActiveUseCase: UpdateServiceReminderActiveUseCase,
        updateServiceReminderIntervalUseCase: UpdateServiceReminderIntervalUseCase,
        updateDistanceUnitUseCase: UpdateDistanceUnitUseCase,
        deleteBikeUseCase: DeleteBikeUseCase,
        getBikesUseCase: GetBikesUseCase,
        saveRideUseCase: SaveRideUseCase,
        getRidesUseCase: GetRidesUseCase,
        deleteRideUseCase: DeleteRideUseCase
    ): MainViewModel {
        return MainViewModel(
            application,
            saveBikeUseCase,
            updateDefaultBikeUseCase,
            updateServiceReminderActiveUseCase,
            updateServiceReminderIntervalUseCase,
            updateDistanceUnitUseCase,
            deleteBikeUseCase,
            getBikesUseCase,
            saveRideUseCase,
            getRidesUseCase,
            deleteRideUseCase
        )
    }
}