package com.example.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.domain.model.DogResponseItem

@Dao
interface DogResponseItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(dog: DogResponseItem): Long // but I have String type Primary Key so how to handle

    @Query("SELECT * FROM dogs")
    fun getAllDogs(): LiveData<List<DogResponseItem>>

    @Delete
    suspend fun deleteDog(dog: DogResponseItem)
}