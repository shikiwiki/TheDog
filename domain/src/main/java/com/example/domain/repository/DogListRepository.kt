package com.example.domain.repository

import com.example.domain.model.Dog

interface DogListRepository {
    suspend fun getDogList() : List<Dog>
}