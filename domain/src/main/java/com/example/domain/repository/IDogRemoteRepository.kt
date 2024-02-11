package com.example.domain.repository

import com.example.domain.model.MDog

interface IDogRemoteRepository {
    suspend fun getDogs(): List<MDog>?
}