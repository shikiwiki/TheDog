package com.example.domain.repository

import com.example.domain.model.DogEntityModel
import com.example.domain.model.Dog

interface IDogLocalRepository {
    suspend fun upsert(dog: DogEntityModel): Long

    fun getLikedDogs(): List<Dog>?

    suspend fun deleteDog(dog: DogEntityModel)
}