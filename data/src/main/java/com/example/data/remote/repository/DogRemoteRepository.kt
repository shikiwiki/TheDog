package com.example.data.remote.repository

import android.util.Log
import com.example.data.remote.network.DogApi
import com.example.data.util.toDomain
import com.example.domain.model.Dog
import com.example.domain.repository.IDogRemoteRepository
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "DogRemoteRepository"

@Singleton
class DogRemoteRepository @Inject constructor(private val api: DogApi) : IDogRemoteRepository {
    override suspend fun getDogs(): MutableList<Dog>? {
        Log.d(TAG, "Getting dogs list.")
        return runCatching {
            val response = api.getData()
            if (response.isSuccessful) {
                response.body()?.map {
                    it.toDomain()
                }?.toMutableList()
            } else {
                throw RuntimeException("Sorry, problem occurred while getting dogs list.")
            }
        }.getOrElse {
            return null
//            throw Exception("Error. Getting dogs list: getOrElse block.")
        }
    }
}