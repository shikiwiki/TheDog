package com.example.domain.model

import java.io.Serializable

data class MDog(
    val id: String,
    val name: String = "",
    val bredFor: String = "",
    val breedGroup: String = "",
    val countryCode: String = "",
    val height: String = "",
    val weight: String = "",
    val number: Int = 0,
    val lifeSpan: String = "",
    val referenceImageId: String = "",
    val temperament: String = "",
    val imageUrl: String,
    val imageWidth: Int,
    val imageHeight: Int
) : Serializable