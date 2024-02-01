package com.example.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.data.local.DogResponseItemDatabase
import com.example.data.retrofit.RetrofitInstance
import com.example.domain.model.DogResponse
import com.example.domain.model.DogResponseItem
import com.example.domain.repository.DogRepository
import retrofit2.Response

//import javax.inject.Inject

private const val TAG = "DogRepositoryImpl"

class DogRepositoryImpl
//@Inject
constructor(
//    private val dogApi: DogApi,
    private val db: DogResponseItemDatabase
) : DogRepository {
    override suspend fun getDogList(): Response<DogResponse> {
        Log.d(TAG, "Getting dogs list.")
        return RetrofitInstance.api.getData()
    }

    override suspend fun upsert(dogResponseItem: DogResponseItem): Long {
        Log.d(TAG, "Adding dog to liked dogs list.")
        return db.getDogResponseItemDao().upsert(dogResponseItem)
    }

    override fun getLikedDogs(): LiveData<List<DogResponseItem>> {
//        Log.d(TAG, "Getting dogs list.")
        return db.getDogResponseItemDao().getAllDogs()
    }

    override suspend fun deleteDog(dogResponseItem: DogResponseItem) {
        Log.d(TAG, "Deleting a dog.")
        db.getDogResponseItemDao().deleteDog(dogResponseItem)
    }
}