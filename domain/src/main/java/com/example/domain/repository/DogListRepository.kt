package com.example.domain.repository

import androidx.lifecycle.LiveData
import com.example.domain.model.DogResponseItem

interface DogListRepository {
    suspend fun getDogList(): List<DogResponseItem>

    suspend fun upsert(dogResponseItem: DogResponseItem): Long

    fun getLikedDogs(): LiveData<List<DogResponseItem>>

    suspend fun deleteDog(dogResponseItem: DogResponseItem)
}