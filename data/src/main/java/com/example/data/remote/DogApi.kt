package com.example.data.remote

import com.example.data.local.entities.DogResponse
import com.example.data.util.Constants.Companion.API_KEY
import com.example.data.util.Constants.Companion.HAS_BREEDS
import com.example.data.util.Constants.Companion.LIMIT_PER_PAGE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/*
Random dog:
https://api.thedogapi.com/v1/images/search?api_key=live_78uH11IyabCdKAj68Sf1exNbi2vyrC2zBBnl0D2m3gWvbYjMjemv4WTZbMWd8e43

10 random dogs
https://api.thedogapi.com/v1/images/search?limit=10&api_key=live_78uH11IyabCdKAj68Sf1exNbi2vyrC2zBBnl0D2m3gWvbYjMjemv4WTZbMWd8e43


https://api.thedogapi.com/v1/breeds?limit=10&has_breeds=1&api_key=live_78uH11IyabCdKAj68Sf1exNbi2vyrC2zBBnl0D2m3gWvbYjMjemv4WTZbMWd8e43
*/

interface DogApi {
    @GET("v1/images/search")
    suspend fun getData(
        @Query("limit")
        limit: String = LIMIT_PER_PAGE.toString(),
        @Query("has_breeds")
        hasBreeds: String = HAS_BREEDS.toString(),
        @Query("api_key")
        apiKey: String = API_KEY
    ): Response<DogResponse>
}