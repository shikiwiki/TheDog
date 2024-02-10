package com.example.thedog

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.local.repository.DogLocalRepository
import com.example.data.remote.repository.DogRemoteRepository

@Suppress("UNCHECKED_CAST")
class DogsViewModelProviderFactory(
    private val localRepository: DogLocalRepository,
    private val remoteRepository: DogRemoteRepository,
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DogsViewModel(remoteRepository, localRepository) as T
    }
}