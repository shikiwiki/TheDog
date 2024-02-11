package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.entities.DogEntity

@Dao
interface DogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(dog: DogEntity): Long

    @Query("SELECT * FROM dogs")
    fun getAllDogs(): List<DogEntity>

    @Delete
    suspend fun deleteDog(dog: DogEntity)
}