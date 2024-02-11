package com.example.data.local.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.local.Converters
import com.example.data.local.dao.DogDao
import com.example.data.local.entities.DogEntity

private const val TAG = "DogDatabase"

@Database(entities = [DogEntity::class], version = 2)
@TypeConverters(Converters::class) //DogEntity lets get rid of it actually
abstract class DogDatabase : RoomDatabase() {
    abstract fun getDao(): DogDao

    companion object {
        @Volatile
        private var instance: DogDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            Log.d(TAG, "Connecting database to instance.")
            instance ?: createDatabase(context).also {
                instance = it
            }

        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                DogDatabase::class.java,
                "dog_db.db")
                .allowMainThreadQueries()
                .build()
    }
}