package com.example.data.remote.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.data.local.db.DogResponseItemDatabase
import com.example.data.remote.retrofit.RetrofitInstance
import com.example.data.util.toDomain
import com.example.domain.model.MDog
import com.example.domain.repository.DogRepository

//import javax.inject.Inject

private const val TAG = "DogRepositoryImpl"

class DogRepositoryImpl
//@Inject
constructor(private val db: DogResponseItemDatabase) : DogRepository {
    override suspend fun getDogs(): List<MDog>? {
        Log.d(TAG, "Getting dogs list.")
        return RetrofitInstance.api.getData().body()?.toDomain()
    }

    override suspend fun upsert(dog: MDog): Long {
        Log.d(TAG, "Adding dog to liked dogs list.")
        return db.getDogResponseItemDao().upsert(dog)
    }

    override fun getLikedDogs(): LiveData<List<MDog>> {
        Log.d(TAG, "Getting dogs list.")
        return db.getDogResponseItemDao().getAllDogs().value?.toDomain().
    }

    override suspend fun deleteDog(dog: MDog) {
        Log.d(TAG, "Deleting a dog.")
        db.getDogResponseItemDao().deleteDog(dog)
    }
}