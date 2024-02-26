package com.example.domain.useCases

import com.example.domain.model.Dog
import com.example.domain.repository.IDogLocalRepository
import com.example.domain.repository.IDogRemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchDogsUseCase @Inject constructor(
    private val remoteRepository: IDogRemoteRepository,
    private val localRepository: IDogLocalRepository
) {

    fun searchDogs(queryString: String): Flow<MutableList<Dog>?> = flow {
        val searchDogsFlow = remoteRepository.searchDogs(queryString)
        val likedDogs = localRepository.getLikedDogs()

        searchDogsFlow.collect { searchDogs ->
            if (searchDogs.isNullOrEmpty()) {
                emit(null)
            } else {
                if (likedDogs.isEmpty()) {
                    emit(searchDogs)
                } else {
                    searchDogs.forEach { dog ->
                        if (likedDogs.contains(dog)) {
                            dog.isLiked = true
                        }
                    }
                    emit(searchDogs)
                }
            }
        }
    }
}