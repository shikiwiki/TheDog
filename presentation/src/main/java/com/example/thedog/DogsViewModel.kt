package com.example.thedog


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.repository.DogLocalRepository
import com.example.data.remote.repository.DogRemoteRepository
import com.example.data.util.Resource
import com.example.data.util.Status
import com.example.data.util.toEntityModel
import com.example.domain.model.Dog
import com.example.domain.useCases.GetAllDogsUseCase
import com.example.domain.useCases.GetLikedDogsUseCase
import kotlinx.coroutines.launch

private const val TAG = "DogsViewModel"

class DogsViewModel(
    private val remoteRepository: DogRemoteRepository,
    private val localRepository: DogLocalRepository
) :
    ViewModel() {
    var page = 1
    val dogsLivaData: MutableLiveData<Resource<MutableList<Dog>>> = MutableLiveData()
    private val likedLogsLivaData = MutableLiveData<Resource<MutableList<Dog>>>()
    private var dogs: MutableList<Dog>? = null
    private val getAllDogsUsecase = GetAllDogsUseCase(remoteRepository, localRepository)
    private val getLikedDogsUsecase = GetLikedDogsUseCase(localRepository)

    init {
        getDogs()
    }

    fun getDogs() = viewModelScope.launch {
        dogsLivaData.postValue(Resource.loading(null))
        val result = getAllDogsUsecase.getAllDogs()
//        val result = remoteRepository.getDogs()
        val resource = Resource.success(result)
        dogsLivaData.postValue(handleDogResponse(resource))
        Log.d(TAG, "All dogs were received in VM.")
    }

    fun addToLikedDogs(dog: Dog) = viewModelScope.launch {
        Log.d(TAG, "Dog ${dog.name} was added to liked dogs.")
        localRepository.upsert(dog.toEntityModel())
    }

    fun getLikedDogs(): LiveData<Resource<MutableList<Dog>>> {
        Log.d(TAG, "Liked dogs were received in VM.")
        val result = localRepository.getLikedDogs()
        val resource = Resource.success(result)
        likedLogsLivaData.value = resource
        return likedLogsLivaData
    }

    fun deleteDog(dog: Dog) = viewModelScope.launch {
        Log.d(TAG, "Dog ${dog.name} was deleted from liked dogs.")
        localRepository.deleteDog(dog.toEntityModel())
    }

    private fun handleDogResponse(resource: Resource<MutableList<Dog>>): Resource<MutableList<Dog>> {
        Log.d(TAG, "Handling DogsResponse.")
        if (resource.status == Status.SUCCESS) {
            resource.data?.let { resultDogs ->
                page++
                if (dogs == null) {
                    dogs = resultDogs
                } else {
                    val oldDogs = dogs
                    oldDogs?.addAll(resultDogs)
                }
                return Resource.success(dogs ?: resultDogs)
            }
        }
        Log.d(TAG, "DogsResponse processed.")
        return Resource.error(null, resource.message.toString())
    }

    fun updateDogs() = viewModelScope.launch {
        dogsLivaData.postValue(Resource.loading(null))
        val result = remoteRepository.getDogs()
        val resource = Resource.success(result)
        dogsLivaData.postValue(handleDogResponseWithUpdate(resource))
        Log.d(TAG, "All dogs were updated in VM.")
    }

    private fun handleDogResponseWithUpdate(resource: Resource<MutableList<Dog>>): Resource<MutableList<Dog>> {
        Log.d(TAG, "Handling DogsResponse with update.")
        if (resource.status == Status.SUCCESS) {
            resource.data?.let { resultDogs ->
                page++
                dogs = resultDogs
                return Resource.success(dogs)
            }
        }
        Log.d(TAG, "Updated DogsResponse processed.")
        return Resource.error(null, resource.message.toString())
    }
}