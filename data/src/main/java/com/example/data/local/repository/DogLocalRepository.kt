package com.example.data.local.repository

import com.example.data.local.dao.DogDao
import com.example.data.util.toDomain
import com.example.data.util.toEntity
import com.example.domain.model.Dog
import com.example.domain.repository.IDogLocalRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DogLocalRepository @Inject constructor(private val dao: DogDao) : IDogLocalRepository {

    override suspend fun upsert(dog: Dog): Long {
        return dao.upsert(dog.toEntity())
    }

    override fun getLikedDogs(): MutableList<Dog> {
        return dao.getAllDogs().map { it.toDomain() }.toMutableList()
    }

    override suspend fun deleteDog(dog: Dog) {
        dao.deleteDog(dog.toEntity())
    }
}