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
import com.example.domain.model.Dog
import com.example.domain.useCases.AllDogsUseCase
import com.example.domain.useCases.LikedDogsUseCase
import kotlinx.coroutines.launch

private const val TAG = "DogsViewModel"
private const val INITIAL_PAGE = 1

class DogsViewModel(
    remoteRepository: DogRemoteRepository,
    localRepository: DogLocalRepository
) : ViewModel() {

    var page = INITIAL_PAGE
    val dogsLivaData: MutableLiveData<Resource<MutableList<Dog>>> = MutableLiveData()
    private val likedLogsLivaData = MutableLiveData<Resource<MutableList<Dog>>>()
    private var dogs: MutableList<Dog>? = null

    private val allDogsUseCase = AllDogsUseCase(remoteRepository, localRepository)
    private val likedDogsUseCase = LikedDogsUseCase(localRepository)

    init {
        getDogs()
    }

    fun getDogs() = viewModelScope.launch {
        dogsLivaData.postValue(Resource.loading(null))
        val result = allDogsUseCase.getAllDogs()
        val resource = Resource.success(result)
        dogsLivaData.postValue(handleDogResponse(resource))
        Log.d(TAG, "All dogs were received in VM.")
    }

    fun addToLikedDogs(dog: Dog) = viewModelScope.launch {
        Log.d(TAG, "Dog ${dog.name} was added to liked dogs.")
        likedDogsUseCase.addToLikedDogs(dog)
        updateDogs()
    }

    fun getLikedDogs(): LiveData<Resource<MutableList<Dog>>> {
        Log.d(TAG, "Liked dogs were received in VM.")
        val result = likedDogsUseCase.getLikedDogs()
        val resource = Resource.success(result)
        likedLogsLivaData.value = resource
        return likedLogsLivaData
    }

    fun deleteDog(dog: Dog) = viewModelScope.launch {
        Log.d(TAG, "Dog ${dog.name} was deleted from liked dogs.")
        likedDogsUseCase.deleteDog(dog)
        getLikedDogs()
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
        return Resource.error(null, "No Internet")
    }

    fun updateDogs() = viewModelScope.launch {
        dogsLivaData.postValue(Resource.loading(null))
        val result = allDogsUseCase.getAllDogs()
        val resource: Resource<MutableList<Dog>> =
            if (result != null) {
                Resource.success(result)
            } else {
                Resource.error(null, "No Internet")
            }
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
        return Resource.error(null, "No Internet")
    }
}