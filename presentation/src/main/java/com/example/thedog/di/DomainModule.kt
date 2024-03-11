package com.example.thedog.di

import com.example.data.local.repository.DogLocalRepository
import com.example.data.remote.repository.DogRemoteRepository
import com.example.domain.useCases.GetAllDogsUseCase
import com.example.domain.useCases.GetLikedDogsUseCase
import com.example.domain.useCases.GetSearchDogsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideAllDogsUseCase(
        remoteRepository: DogRemoteRepository,
        localRepository: DogLocalRepository
    ): GetAllDogsUseCase = GetAllDogsUseCase(remoteRepository, localRepository)

    @Provides
    fun provideLikedDogsUseCase(
        localRepository: DogLocalRepository
    ): GetLikedDogsUseCase = GetLikedDogsUseCase(localRepository)

    @Provides
    fun provideSearchDogsUseCase(
        remoteRepository: DogRemoteRepository,
        localRepository: DogLocalRepository
    ): GetSearchDogsUseCase = GetSearchDogsUseCase(remoteRepository, localRepository)
}