package com.example.domain.repository

import com.example.domain.model.DogEntityModel
import com.example.domain.model.MDog

interface IDogLocalRepository {
    suspend fun upsert(dog: DogEntityModel): Long

    fun getLikedDogs(): List<MDog>?

    suspend fun deleteDog(dog: DogEntityModel)
}