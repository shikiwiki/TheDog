package com.example.data.util

import com.example.data.local.entities.DogEntity
import com.example.data.remote.dto.BreedResponseItem
import com.example.data.remote.dto.DogResponseItem
import com.example.domain.model.Dog

fun DogResponseItem.toDomain(): Dog {
    return Dog(
        id = this.id,
        name = this.breeds[0].name,
        bredFor = this.breeds[0].bred_for,
        breedGroup = this.breeds[0].breed_group,
        height = this.breeds[0].height.metric,
        weight = this.breeds[0].weight.metric,
        number = this.breeds[0].id,
        lifeSpan = this.breeds[0].life_span,
        referenceImageId = this.breeds[0].reference_image_id,
        temperament = this.breeds[0].temperament,
        imageUrl = this.url,
        imageWidth = this.width,
        imageHeight = this.height
    )
}

fun BreedResponseItem.toDomain(): Dog {
    return Dog(
        id = this.image.id,
        name = this.name,
        bredFor = this.bred_for,
        breedGroup = this.breed_group,
        height = this.height.metric,
        weight = this.weight.metric,
        number = this.id.toInt(),
        lifeSpan = this.life_span,
        referenceImageId = this.reference_image_id,
        temperament = this.temperament,
        imageUrl = this.image.url,
        imageWidth = this.image.width.toInt(),
        imageHeight = this.image.height.toInt()
    )
}

fun DogEntity.toDomain(): Dog {
    return Dog(
        id = this.id,
        name = this.name,
        bredFor = this.bred_for,
        breedGroup = this.breed_group,
        height = this.height,
        weight = this.weight,
        number = this.number,
        lifeSpan = this.life_span,
        referenceImageId = this.reference_image_id,
        temperament = this.temperament,
        imageUrl = this.imageUrl,
        imageWidth = this.imageWidth,
        imageHeight = this.imageHeight
    )
}

fun Dog.toEntity(): DogEntity {
    return DogEntity(
        id = this.id,
        name = this.name,
        bred_for = this.bredFor,
        breed_group = this.breedGroup,
        height = this.height,
        weight = this.weight,
        number = this.number,
        life_span = this.lifeSpan,
        reference_image_id = this.referenceImageId,
        temperament = this.temperament,
        imageUrl = this.imageUrl,
        imageWidth = this.imageWidth,
        imageHeight = this.imageHeight
    )
}