package com.example.domain.repository

import com.example.domain.model.Dog

interface IDogLocalRepository {
    suspend fun upsert(dog: Dog): Long

    fun getLikedDogs(): MutableList<Dog>

    suspend fun deleteDog(dog: Dog)
}