package com.example.domain.useCases

import com.example.domain.model.Dog
import com.example.domain.repository.IDogLocalRepository

class GetLikedDogsUseCase(private val localRepository: IDogLocalRepository) {
    fun getLikedDogs(): MutableList<Dog>? {
        return localRepository.getLikedDogs()
    }
}