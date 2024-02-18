package com.example.domain.useCases

import com.example.domain.model.Dog
import com.example.domain.repository.IDogLocalRepository
import com.example.domain.repository.IDogRemoteRepository

class AllDogsUseCase(
    private val remoteRepository: IDogRemoteRepository,
    private val localRepository: IDogLocalRepository
) {
    suspend fun getAllNotLikedDogs(): MutableList<Dog>? {
        val allDogs = remoteRepository.getDogs()
        if (allDogs.isNullOrEmpty()) {
            return null
        }
        val likedDogs = localRepository.getLikedDogs()
        likedDogs?.let { allDogs.removeAll(it) }

        return allDogs
    }

    suspend fun getAllDogs(): MutableList<Dog>? {
        val allDogs = remoteRepository.getDogs()
        if (allDogs.isNullOrEmpty()) {
            return null
        }
        val likedDogs = localRepository.getLikedDogs()

        if (likedDogs.isNullOrEmpty()) {
            return allDogs
        }

        allDogs.forEach {
            if (likedDogs.contains(it)) {
                it.isLiked = true
            }
        }
        return allDogs
    }
}