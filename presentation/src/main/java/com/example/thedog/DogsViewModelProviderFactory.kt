package com.example.thedog

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.remote.repository.DogRepositoryImpl

@Suppress("UNCHECKED_CAST")
class DogsViewModelProviderFactory(
    val app: Application,
    private val dogRepository: DogRepositoryImpl
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DogsViewModel(app, dogRepository) as T
    }
}