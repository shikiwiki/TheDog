package com.example.domain.model

data class MDog(
    val id: String,
    val name: String = "",
    val bred_for: String = "",
    val breed_group: String = "",
    val country_code: String = "",
    val height: Int = 0,
    val weight: Int = 0,
    val number: Int = 0,
    val life_span: String = "",
    val reference_image_id: String = "",
    val temperament: String = "",
    val imageUrl: String,
    val imageWidth: Int,
    val imageHeight: Int,
)
