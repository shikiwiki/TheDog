package com.example.data.remote.repository

import android.util.Log
import com.example.data.remote.api.DogApi
import com.example.data.util.toDomain
import com.example.domain.model.MDog
import com.example.domain.repository.IDogRemoteRepository

private const val TAG = "DogApiRepository"

class DogRemoteRepository(
    val api: DogApi
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
//
//    override suspend fun getAllCharacters(offset: Int, limit: Int): List<CharacterItemModel> {
//        val formattedTimestamp = timestamp.generateFormattedTimestamp(timestamp.generateTimestamp())
//        val hash = md5.md5(formattedTimestamp + PRIVATE_KEY + PUBLIC_KEY)
//        return runCatching {
//            val response =
//                retrofit.getCharacters(formattedTimestamp, PUBLIC_KEY, hash, offset, limit)
//            if (response.code == 200) {
//                response.data.results.map { it.toCharacterItemModel() }
//            } else {
//                throw MarvelApiException("Error ${response.code} ${response.status}")
//            }
//        }.getOrElse {
//            throw MarvelApiException("Error during network request ${it.localizedMessage} ${it.cause} ${it.message}")
//        }
//    }
}