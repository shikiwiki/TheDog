package com.example.data.remote.network

import android.util.Log
import com.example.data.util.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "RetrofitInstance"

class RetrofitInstance {
    companion object {
        private val retrofit by lazy {
            Log.d(TAG, "Instantiating retrofit.")
            val logger = HttpLoggingInterceptor()
            logger.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
        val api: DogApi by lazy {
            retrofit.create(DogApi::class.java)
        }
    }
}