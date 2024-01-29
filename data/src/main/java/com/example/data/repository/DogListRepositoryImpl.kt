package com.example.data.repository

import androidx.lifecycle.LiveData
import com.example.data.local.DogResponseItemDatabase
import com.example.data.retrofit.RetrofitInstance
import com.example.domain.model.DogResponseItem
import com.example.domain.repository.DogListRepository

//import javax.inject.Inject

class DogListRepositoryImpl
//@Inject
constructor(
//    private val dogApi: DogApi,
    private val db: DogResponseItemDatabase
) : DogListRepository {
    override suspend fun getDogList(): List<DogResponseItem> =
        RetrofitInstance.api.getData()

    override suspend fun upsert(dogResponseItem: DogResponseItem): Long =
        db.getDogResponseItemDao().upsert(dogResponseItem)

    override fun getLikedDogs(): LiveData<List<DogResponseItem>> =
        db.getDogResponseItemDao().getAllDogs()

    override suspend fun deleteDog(dogResponseItem: DogResponseItem) {
        db.getDogResponseItemDao().deleteDog(dogResponseItem)
    }
}