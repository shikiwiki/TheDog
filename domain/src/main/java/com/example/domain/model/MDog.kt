package com.example.domain.model

import java.io.Serializable

data class MDog(
    val id: String,
    val name: String = "",
    val bred_for: String = "",
    val breed_group: String = "",
    val country_code: String = "",
    val height: String = "",
    val weight: String = "",
    val number: Int = 0,
    val life_span: String = "",
    val reference_image_id: String = "",
    val temperament: String = "",
    val imageUrl: String,
    val imageWidth: Int,
    val imageHeight: Int
) : Serializable
