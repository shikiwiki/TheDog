package com.example.data.di

//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
import com.example.data.remote.DogApi
import retrofit2.Retrofit
//import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
object AppModule {

    //    @Provides @Singleton
    fun provideApi(): DogApi {
        return Retrofit.Builder()
            .baseUrl("https://api.thedogapi.com/")
            .build()
            .create(DogApi::class.java)
    }

    //    @Provides @Singleton
//    fun provideRepository(api: DogApi): DogListRepository {
//        return DogListRepositoryImpl(api)
//    }
}