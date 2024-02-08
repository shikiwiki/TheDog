package com.example.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "dogs")
data class DogResponseItem(
    val breeds: List<Breed>,
    val height: Int,
    @PrimaryKey
    val id: String,
    val url: String,
    val width: Int
) : Serializable