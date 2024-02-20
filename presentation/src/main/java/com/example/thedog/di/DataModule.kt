package com.example.thedog.di

import android.content.Context
import androidx.room.Room
import com.example.data.local.dao.DogDao
import com.example.data.local.db.DogDatabase
import com.example.data.local.repository.DogLocalRepository
import com.example.data.remote.network.DogApi
import com.example.data.remote.repository.DogRemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): DogDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            DogDatabase::class.java,
            "dog_db.db"
        )
            .allowMainThreadQueries()
            .build()
    }

//    @Provides
//    fun provideDogDao(database: CharactersLocalRepository): CharactersDao {
//        return database.characterDao()
//    }


    @Provides
    @Singleton
    fun provideDogLocalRepository(dao: DogDao): DogLocalRepository {
        return DogLocalRepository(dao)
    }

    @Provides
    @Singleton
    fun provideDogRemoteRepository(api: DogApi): DogRemoteRepository {
        return DogRemoteRepository(api)
    }
}