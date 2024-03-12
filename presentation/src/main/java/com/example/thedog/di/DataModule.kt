package com.example.thedog.di

import android.content.Context
import androidx.room.Room
import com.example.data.BuildConfig
import com.example.data.local.dao.DogDao
import com.example.data.local.db.DogDatabase
import com.example.data.remote.network.DogApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides @Singleton
    fun provideDogDatabase(@ApplicationContext context: Context): DogDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            DogDatabase::class.java,
            "dog_db.db"
        ).allowMainThreadQueries().build()

    @Provides @Singleton
    fun provideDogDao(dogDatabase: DogDatabase): DogDao = dogDatabase.getDao()

    @Provides @Singleton
    fun provideRetrofit(): Retrofit {
        val logger = HttpLoggingInterceptor()
        logger.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides @Singleton
    fun provideDogApi(retrofit: Retrofit): DogApi = retrofit.create(DogApi::class.java)
}