package com.example.domain.repository

import com.example.domain.model.MDog
import com.example.domain.model.MDogItem

interface DogRepository {
    suspend fun getDogList(): MDog

    suspend fun upsert(dogResponseItem: MDogItem): Long

    fun getLikedDogs(): List<MDogItem>

    suspend fun deleteDog(dogResponseItem: MDogItem)
}