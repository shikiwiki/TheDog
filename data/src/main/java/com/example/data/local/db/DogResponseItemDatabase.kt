package com.example.data.local.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.local.Converters
import com.example.data.local.dao.DogResponseItemDao
import com.example.data.local.entities.DogResponseItem

private const val TAG = "DogResponseItemDatabase"

@Database(entities = [DogResponseItem::class], version = 1)
@TypeConverters(Converters::class)
abstract class DogResponseItemDatabase : RoomDatabase() {
    abstract fun getDogResponseItemDao(): DogResponseItemDao

    companion object {
        @Volatile
        private var instance: DogResponseItemDatabase? = null
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
                DogResponseItemDatabase::class.java,
                "dog_db.db"
            ).build()
    }
}