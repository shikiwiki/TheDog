package com.example.domain.repository

import com.example.domain.model.Dog

interface IDogRemoteRepository {
    suspend fun getDogs(): List<Dog>?
}