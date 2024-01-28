package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DogEntity(

    @PrimaryKey
    val id: Long?,
    val name: String?,
    val imageUrl: String?,
    val description: String?
)