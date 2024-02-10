package com.example.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.DogEntityModel
import com.example.domain.model.MDog

@Entity(tableName = "dogs")
class DogEntity(
    @PrimaryKey
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
)

fun DogEntity.toEntityModel(): DogEntityModel {
    return DogEntityModel(
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

fun DogEntityModel.toEntity(): DogEntity {
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
        imageHeight = this.imageWidth
    )
}

fun MDog.toEntityModel(): DogEntityModel {
    return DogEntityModel(
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
