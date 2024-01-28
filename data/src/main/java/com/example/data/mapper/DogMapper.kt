package com.example.data.mapper

import com.example.data.local.DogEntity
import com.example.domain.model.Dog

fun DogEntity.toDog() = Dog(  //Dto == Entity?
    id = id,
    name = name,
    imageUrl = imageUrl,
    description = description
)
