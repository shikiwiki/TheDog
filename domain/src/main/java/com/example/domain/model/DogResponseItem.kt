package com.example.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "dogs")
data class DogResponseItem(
    val breeds: List<Breed>,
    @PrimaryKey
    val id: String,
    val url: String,
    val width: Int,
    val height: Int,
) : Serializable
