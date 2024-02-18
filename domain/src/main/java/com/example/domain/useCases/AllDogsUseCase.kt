package com.example.domain.useCases

import com.example.domain.model.Dog
import com.example.domain.repository.IDogLocalRepository
import com.example.domain.repository.IDogRemoteRepository

class AllDogsUseCase(
    private val remoteRepository: IDogRemoteRepository,
    private val localRepository: IDogLocalRepository
) {
    suspend fun getAllDogs(): MutableList<Dog>? {
        val likedDogs = localRepository.getLikedDogs()
        val allDogs = remoteRepository.getDogs()
        if (allDogs.isNullOrEmpty()) {
            return null
        }
        likedDogs?.let { allDogs.removeAll(it) }

        return allDogs
    }
}