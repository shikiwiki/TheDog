package com.example.domain.repository

import com.example.domain.model.DogToDelete

interface DogListRepository {
    suspend fun getDogList() : List<DogToDelete>
}