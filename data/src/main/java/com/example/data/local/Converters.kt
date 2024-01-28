package com.example.data.local

import androidx.room.TypeConverter
import com.example.domain.model.Breed

class Converters {
    @TypeConverter
    fun fromBreeds(breeds: List<Breed>): String {
        val sb = StringBuffer("")
        breeds.forEach { breed ->
            sb
                .append(breed.name).append(",")
                .append(breed.temperament).append(",")
                .append(breed.life_span)
        }
        return sb.toString()
    }

    @TypeConverter
    fun toBreeds(breedInfo: String): List<Breed> {
        val infoItems = breedInfo.split(",")
        val name = infoItems[0]
        val temperament = infoItems[1]
        val lifeSpan = infoItems[2]
        return listOf(
            Breed(name = name, life_span = lifeSpan, temperament = temperament)
        )
    }
}