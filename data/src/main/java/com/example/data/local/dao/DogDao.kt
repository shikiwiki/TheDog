package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.entities.DogEntity
import com.example.domain.model.MDog

@Dao
interface DogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(dog: DogEntity): Long // but I have String type Primary Key so how to handle

    @Query("SELECT * FROM dogs")
    fun getAllDogs(): List<DogEntity>

    @Delete
    suspend fun deleteDog(dog: DogEntity)
}