package com.example.domain.useCases

import com.example.domain.model.Dog
import com.example.domain.repository.IDogLocalRepository
import com.example.domain.repository.IDogRemoteRepository

class GetAllDogsUseCase(
    private val remoteRepository: IDogRemoteRepository,
    private val localRepository: IDogLocalRepository
) {
    suspend fun getAllDogs(): MutableList<Dog>? {
        return remoteRepository.getDogs()
    }
}