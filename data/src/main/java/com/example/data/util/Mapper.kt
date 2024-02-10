package com.example.data.util

import com.example.data.local.entities.DogEntity
import com.example.data.remote.dto.DogResponse
import com.example.data.remote.dto.DogResponseItem
import com.example.domain.model.MDog

fun DogResponse.toDomain(): List<MDog> {
    return this.map { it.toDomain() }.toMutableList()
}

fun DogResponseItem.toDomain(): MDog {
    return MDog(
        id = this.id,
        name = this.breeds[0].name,
        bred_for = this.breeds[0].bred_for,
        breed_group = this.breeds[0].breed_group,
        country_code = this.breeds[0].country_code,
        height = this.breeds[0].height.metric,
        weight = this.breeds[0].weight.metric,
        number = this.breeds[0].id,
        life_span = this.breeds[0].life_span,
        reference_image_id = this.breeds[0].reference_image_id,
        temperament = this.breeds[0].temperament,
        imageUrl = this.url,
        imageWidth = this.width,
        imageHeight = this.height
    )
}

fun List<DogResponseItem>.toDomain(): List<MDog> {
    return this.map { it.toDomain() }.toMutableList()
}

fun MDog.toEntity() : DogEntity {
    return DogEntity(
        id = this.id,
        name = this.name,
        bred_for = this.bred_for,
        breed_group = this.breed_group,
        country_code = this.country_code,
        height = this.height,
        weight = this.weight,
        number = this.number,
        life_span = this.life_span,
        reference_image_id = this.reference_image_id,
        temperament = this.temperament,
        imageUrl = this.imageUrl,
        imageWidth = this.imageWidth,
        imageHeight = this.imageHeight
    )
}

fun DogEntity.toDomain() : MDog {
    return MDog(
        id = this.id,
        name = this.name,
        bred_for = this.bred_for,
        breed_group = this.breed_group,
        country_code = this.country_code,
        height = this.height,
        weight = this.weight,
        number = this.number,
        life_span = this.life_span,
        reference_image_id = this.reference_image_id,
        temperament = this.temperament,
        imageUrl = this.imageUrl,
        imageWidth = this.imageWidth,
        imageHeight = this.imageHeight
    )
}