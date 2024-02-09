package com.example.data.local.repository

import android.util.Log
import com.example.data.local.dao.DogDao
import com.example.data.local.db.DogDatabase
import com.example.data.local.entities.toEntity
import com.example.data.local.entities.toEntityModel
import com.example.data.util.toDomain
import com.example.data.util.toEntity
import com.example.domain.model.DogEntityModel
import com.example.domain.model.MDog
import com.example.domain.repository.IDogLocalRepository

//import javax.inject.Inject

private const val TAG = "DogRepositoryImpl"


class DogLocalRepository
//@Inject
constructor(private val dao: DogDao) : IDogLocalRepository {

    override suspend fun upsert(dog: DogEntityModel): Long {
        Log.d(TAG, "Adding dog to liked dogs list.")
        return dao.upsert(dog.toEntity())
    }

    override fun getLikedDogs(): List<MDog> {
        Log.d(TAG, "Getting dogs list.")
//        val liveData = LiveData<List<MDog>>
        return dao.getAllDogs().map { it.toDomain() }
    }

    override suspend fun deleteDog(dog: DogEntityModel) {
        Log.d(TAG, "Deleting a dog.")
        dao.deleteDog(dog.toEntity())
    }
}