package com.example.domain.repository

import androidx.lifecycle.LiveData
import com.example.domain.model.DogResponse
import com.example.domain.model.DogResponseItem
import retrofit2.Response

interface DogRepository {
    suspend fun getDogList(): Response<DogResponse>

    suspend fun upsert(dogResponseItem: DogResponseItem): Long

    fun getLikedDogs(): LiveData<List<DogResponseItem>>

    suspend fun deleteDog(dogResponseItem: DogResponseItem)
}