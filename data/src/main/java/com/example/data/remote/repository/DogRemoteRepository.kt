package com.example.data.remote.repository

import android.util.Log
import com.example.data.remote.network.DogApi
import com.example.data.util.toDomain
import com.example.domain.model.Dog
import com.example.domain.repository.IDogRemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "DogRemoteRepository"

@Singleton
class DogRemoteRepository @Inject constructor(private val api: DogApi) : IDogRemoteRepository {
    override suspend fun getDogs(): Flow<MutableList<Dog>?> = flow {
        Log.d(TAG, "Getting dogs list.")
        runCatching {
            val response = api.getData()
            if (response.isSuccessful) {
                emit(response.body()?.map { it.toDomain() }?.toMutableList())
            } else {
                throw RuntimeException("Sorry, problem occurred while getting dogs list.")
            }
        }.getOrElse {
            emit(null)
        }
    }

    override suspend fun searchDogs(searchQuery: String): Flow<MutableList<Dog>?> = flow {
        Log.d(TAG, "Searching for dogs.")
        runCatching {
            val response = api.searchData(searchQuery)
            if (response.isSuccessful) {
                emit(response.body()?.map { it.toDomain() }?.toMutableList())
            } else {
                throw RuntimeException("Sorry, problem occurred while searching dogs.")
            }
        }.getOrElse {
            emit(null)
        }
    }
}