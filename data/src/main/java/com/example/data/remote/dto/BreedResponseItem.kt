package com.example.data.remote.dto

@Suppress("PropertyName")
data class BreedResponseItem(
    val weight: Weight,
    val height: Height,
    val id: Long,
    val name: String,
    val bred_for: String,
    val breed_group: String,
    val life_span: String,
    val temperament: String,
    val reference_image_id: String,
    val image: Image,
)