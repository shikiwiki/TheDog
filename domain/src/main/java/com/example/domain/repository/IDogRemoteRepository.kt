package com.example.domain.repository

import com.example.domain.model.Dog
import kotlinx.coroutines.flow.Flow

interface IDogRemoteRepository {
    suspend fun getDogs(): Flow<MutableList<Dog>?>

    suspend fun searchDogs(searchQuery: String): Flow<MutableList<Dog>?>
}