package com.example.data.remote.dto

data class Breed(
    val bred_for: String = "",
    val breed_group: String = "",
    val country_code: String = "",
    val height: Height = Height("0", "0"),
    val id: Int = 0,
    val life_span: String = "",
    val name: String = "",
    val reference_image_id: String = "",
    val temperament: String = "",
    val weight: Weight = Weight("0", "0")
)