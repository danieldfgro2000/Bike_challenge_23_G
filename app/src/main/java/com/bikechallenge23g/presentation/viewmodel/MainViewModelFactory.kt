package com.bikechallenge23g.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bikechallenge23g.domain.usecase.DeleteBikeUseCase
import com.bikechallenge23g.domain.usecase.GetBikesUseCase
import com.bikechallenge23g.domain.usecase.SaveBikeUseCase

class MainViewModelFactory(
    private val application: Application,
    private val getBikesUseCase: GetBikesUseCase,
    private val saveBikeUseCase: SaveBikeUseCase,
    private val deleteBikeUseCase: DeleteBikeUseCase
) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(
            application,
            saveBikeUseCase,
            deleteBikeUseCase,
            getBikesUseCase
        ) as T
    }
}