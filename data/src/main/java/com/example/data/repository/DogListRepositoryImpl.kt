package com.example.data.repository

import com.example.data.remote.DogApi
import com.example.domain.model.DogToDelete
import com.example.domain.repository.DogListRepository
//import javax.inject.Inject

class DogListRepositoryImpl
//@Inject
constructor(
    private val dogApi: DogApi,
//    private val dogDatabase: DogDatabase
) :
    DogListRepository {
    override suspend fun getDogList(): List<DogToDelete> {
//        return dogApi.getData().map { it.toDog() }
        return emptyList()  //stub to be compiled
    }
}