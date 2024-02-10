package com.example.data.remote.repository

import android.util.Log
import com.example.data.remote.api.DogApi
import com.example.data.util.toDomain
import com.example.domain.model.MDog
import com.example.domain.repository.IDogRemoteRepository

private const val TAG = "DogApiRepository"

class DogRemoteRepository(
    private val api: DogApi
) : IDogRemoteRepository {
    override suspend fun getDogs(): List<MDog>? {
        Log.d(TAG, "Getting dogs list.")
        return runCatching {
            val response = api.getData()
            if (response.isSuccessful) {
                response.body()?.map {
                    it.toDomain()
                }
            } else {
                throw RuntimeException("Sorry")
            }
        }.getOrElse {
            throw Exception("Error")
        }
    }
}