package com.example.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "dogs")
data class DogResponseItem(
    @PrimaryKey(autoGenerate = true)
    var autoId: Int? = null,
    val breeds: List<Breed>,
    val id: String,
    val url: String,
    val width: Int,
    val height: Int,
) : Serializable
