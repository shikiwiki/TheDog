package com.example.data.util

import com.example.data.local.entities.DogEntity
import com.example.data.remote.dto.DogResponseItem
import com.example.domain.model.DogEntityModel
import com.example.domain.model.Dog

fun DogResponseItem.toDomain(): Dog {
    return Dog(
        id = this.id,
        name = this.breeds[0].name,
        bredFor = this.breeds[0].bred_for,
        breedGroup = this.breeds[0].breed_group,
        countryCode = this.breeds[0].country_code,
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

fun DogEntity.toDomain(): Dog {
    return Dog(
        id = this.id,
        name = this.name,
        bredFor = this.bred_for,
        breedGroup = this.breed_group,
        countryCode = this.country_code,
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

fun DogEntityModel.toEntity(): DogEntity {
    return DogEntity(
        id = this.id,
        name = this.name,
        bred_for = this.bredFor,
        breed_group = this.breedGroup,
        country_code = this.countryCode,
        height = this.height,
        weight = this.weight,
        number = this.number,
        life_span = this.lifeSpan,
        reference_image_id = this.referenceImageId,
        temperament = this.temperament,
        imageUrl = this.imageUrl,
        imageWidth = this.imageWidth,
        imageHeight = this.imageWidth
    )
}

fun Dog.toEntityModel(): DogEntityModel {
    return DogEntityModel(
        id = this.id,
        name = this.name,
        bredFor = this.bredFor,
        breedGroup = this.breedGroup,
        countryCode = this.countryCode,
        height = this.height,
        weight = this.weight,
        number = this.number,
        lifeSpan = this.lifeSpan,
        referenceImageId = this.referenceImageId,
        temperament = this.temperament,
        imageUrl = this.imageUrl,
        imageWidth = this.imageWidth,
        imageHeight = this.imageHeight
    )
}


//fun DogResponse.toDomain(): List<MDog> {
//    return this.map { it.toDomain() }.toMutableList()
//}

//fun List<DogResponseItem>.toDomain(): List<MDog> {
//    return this.map { it.toDomain() }.toMutableList()
//}

//fun MDog.toEntity(): DogEntity {
//    return DogEntity(
//        id = this.id,
//        name = this.name,
//        bred_for = this.bred_for,
//        breed_group = this.breed_group,
//        country_code = this.country_code,
//        height = this.height,
//        weight = this.weight,
//        number = this.number,
//        life_span = this.life_span,
//        reference_image_id = this.reference_image_id,
//        temperament = this.temperament,
//        imageUrl = this.imageUrl,
//        imageWidth = this.imageWidth,
//        imageHeight = this.imageHeight
//    )
//}

//fun DogEntity.toEntityModel(): DogEntityModel {
//    return DogEntityModel(
//        id = this.id,
//        name = this.name,
//        bred_for = this.bred_for,
//        breed_group = this.breed_group,
//        country_code = this.country_code,
//        height = this.height,
//        weight = this.weight,
//        number = this.number,
//        life_span = this.life_span,
//        reference_image_id = this.reference_image_id,
//        temperament = this.temperament,
//        imageUrl = this.imageUrl,
//        imageWidth = this.imageWidth,
//        imageHeight = this.imageHeight
//    )
//}