package com.bikechallenge23g.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bikechallenge23g.domain.usecase.GetBikesUseCase

class MainViewModelFactory (
    private val application: Application,
    private val getBikesUseCase: GetBikesUseCase
) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(
            application,
            getBikesUseCase
        ) as T
    }
}