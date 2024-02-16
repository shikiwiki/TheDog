package com.example.data.local.repository

import android.util.Log
import com.example.data.local.dao.DogDao
import com.example.data.util.toDomain
import com.example.data.util.toEntity
import com.example.domain.model.Dog
import com.example.domain.repository.IDogLocalRepository

private const val TAG = "DogLocalRepository"

class DogLocalRepository(private val dao: DogDao) : IDogLocalRepository {

    override suspend fun upsert(dog: Dog): Long {
        Log.d(TAG, "Adding ${dog.name} to list of liked dogs.")
        return dao.upsert(dog.toEntity())
    }

    override fun getLikedDogs(): MutableList<Dog> { // make it return LiveDate!
        Log.d(TAG, "Getting list of liked dogs.")
        return dao.getAllDogs().map { it.toDomain() }.toMutableList()
    }

    override suspend fun deleteDog(dog: Dog) {
        Log.d(TAG, "Deleting ${dog.name} from list of liked dogs.")
        dao.deleteDog(dog.toEntity())
    }
}