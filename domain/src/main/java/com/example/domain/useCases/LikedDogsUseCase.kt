package com.example.domain.useCases

import com.example.domain.model.Dog
import com.example.domain.repository.IDogLocalRepository

class LikedDogsUseCase(private val localRepository: IDogLocalRepository) {
    fun getLikedDogs(): MutableList<Dog>? {
        return localRepository.getLikedDogs()
    }

    suspend fun deleteDog(dog: Dog) {
        localRepository.deleteDog(dog)
    }

    suspend fun addToLikedDogs(dog: Dog) {
        localRepository.upsert(dog)
    }
}
