package com.example.data.remote.network

import com.example.data.remote.dto.BreedResponse
import com.example.data.remote.dto.DogResponse
import com.example.data.util.Constants.Companion.API_KEY
import com.example.data.util.Constants.Companion.HAS_BREEDS
import com.example.data.util.Constants.Companion.LIMIT_PER_PAGE
import com.example.data.util.Constants.Companion.ORDER_RANDOM
import com.example.data.util.Constants.Companion.PAGE
import com.example.data.util.Constants.Companion.SEARCH_LIMIT_PER_PAGE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DogApi {
    @GET("v1/images/search")
    suspend fun getData(
        @Query("limit")
        limit: String = LIMIT_PER_PAGE.toString(),
        @Query("order")
        order: String = ORDER_RANDOM,
        @Query("has_breeds")
        hasBreeds: Int = HAS_BREEDS,
        @Query("api_key")
        apiKey: String = API_KEY
    ): Response<DogResponse>

    @GET("v1/breeds/search")
    suspend fun searchData(
        @Query("q")
        searchQuery: String,
        @Query("limit")
        limit: String = SEARCH_LIMIT_PER_PAGE.toString(),
        @Query("page")
        page: String = PAGE.toString(),
        @Query("api_key")
        apiKey: String = API_KEY
    ): Response<BreedResponse>
}