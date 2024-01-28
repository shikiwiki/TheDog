package com.example.domain.model

data class TheDogResponseItem(
    val breeds: MutableList<Breed>,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)
