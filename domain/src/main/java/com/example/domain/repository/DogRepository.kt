package com.example.domain.repository

import com.example.domain.model.MDog

interface DogRepository {
    suspend fun getDogs(): List<MDog>?

    suspend fun upsert(dog: MDog): Long

    fun getLikedDogs(): List<MDog>?

    suspend fun deleteDog(dog: MDog)
}