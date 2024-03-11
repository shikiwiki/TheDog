package com.example.domain.useCases

import com.example.domain.model.Dog
import com.example.domain.repository.IDogLocalRepository
import com.example.domain.repository.IDogRemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllDogsUseCase @Inject constructor(
    private val remoteRepository: IDogRemoteRepository,
    private val localRepository: IDogLocalRepository
) {

    suspend fun getAllDogs(): Flow<MutableList<Dog>?> = flow {
        val allDogsFlow = remoteRepository.getDogs()
        val likedDogs = localRepository.getLikedDogs()

        allDogsFlow.collect { allDogs ->
            if (allDogs.isNullOrEmpty()) {
                emit(null)
            } else {
                if (likedDogs.isNotEmpty()) {
                    allDogs.forEach { dog -> dog.isLiked = likedDogs.contains(dog) }
                }
                emit(allDogs)
            }
        }
    }
}