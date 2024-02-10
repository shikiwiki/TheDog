package com.example.data.remote.dto

data class DogResponseItem(
    val breeds: List<Breed>,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)