package com.example.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.dao.DogDao
import com.example.data.local.entities.DogEntity

@Database(entities = [DogEntity::class], version = 2, exportSchema = false)
abstract class DogDatabase : RoomDatabase() {
    abstract fun getDao(): DogDao
}