package com.example.data.local

import android.util.Log
import androidx.room.TypeConverter
import com.example.data.local.entities.Breed
import com.example.domain.model.MBreed

private const val TAG = "Converters"

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
        Log.d(TAG, "Converting List<Breed> to String.")
        return sb.toString()
    }

    @TypeConverter
    fun toBreeds(breedInfo: String): List<Breed> {
        val infoItems = breedInfo.split(",")
        val name = infoItems[0]
        val temperament = infoItems[1]
        val lifeSpan = infoItems[2]
        Log.d(TAG, "Converting String to List<Breed> .")
        return listOf(
            Breed(name = name, life_span = lifeSpan, temperament = temperament)
        )
    }
}