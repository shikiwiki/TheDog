package com.example.thedog.di

import com.example.data.local.repository.DogLocalRepository
import com.example.data.remote.repository.DogRemoteRepository
import com.example.domain.useCases.AllDogsUseCase
import com.example.domain.useCases.LikedDogsUseCase
import com.example.domain.useCases.SearchDogsUseCase
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
    ): AllDogsUseCase = AllDogsUseCase(remoteRepository, localRepository)

    @Provides
    fun provideLikedDogsUseCase(
        localRepository: DogLocalRepository
    ): LikedDogsUseCase = LikedDogsUseCase(localRepository)

    @Provides
    fun provideSearchDogsUseCase(
        remoteRepository: DogRemoteRepository,
        localRepository: DogLocalRepository
    ): SearchDogsUseCase = SearchDogsUseCase(remoteRepository, localRepository)
}