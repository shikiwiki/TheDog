package com.example.thedog


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.util.Resource
import com.example.data.util.Status
import com.example.domain.model.Dog
import com.example.domain.useCases.AllDogsUseCase
import com.example.domain.useCases.LikedDogsUseCase
import com.example.domain.useCases.SearchDogsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "DogsViewModel"
private const val INITIAL_All_DOGS_PAGE = 1
private const val INITIAL_SEARCH_DOGS_PAGE = 1

@HiltViewModel
class DogsViewModel @Inject constructor(
    private val allDogsUseCase: AllDogsUseCase,
    private val likedDogsUseCase: LikedDogsUseCase,
    private val searchDogsUseCase: SearchDogsUseCase
) : ViewModel() {

    val allDogsLivaData = MutableLiveData<Resource<MutableList<Dog>>>()
    var allDogsPage = INITIAL_All_DOGS_PAGE
    private var dogs: MutableList<Dog>? = null

    val searchDogsLivaData = MutableLiveData<Resource<MutableList<Dog>>>()
    var searchDogsPage = INITIAL_SEARCH_DOGS_PAGE
    private var searchDogs: MutableList<Dog>? = null

    private val likedDogsLivaData = MutableLiveData<Resource<MutableList<Dog>>>()


    init {
        getDogs()
    }

    fun getDogs() = viewModelScope.launch {
        allDogsLivaData.postValue(Resource.loading(null))
        val allDogsFlow = allDogsUseCase.getAllDogs()
        allDogsFlow.collect { allDogs ->
            val resource = Resource.success(allDogs)
            allDogsLivaData.postValue(handleDogResponse(resource))
        }
        Log.d(TAG, "All dogs were received in VM.")
    }

    fun searchDogs(searchQuery: String) = viewModelScope.launch {
        searchDogsLivaData.postValue(Resource.loading(null))
        val searchDogsFlow = searchDogsUseCase.searchDogs(searchQuery)
        searchDogsFlow.collect {searchDogs ->
            val resource = Resource.success(searchDogs)
            searchDogsLivaData.postValue(handleSearchDogResponse(resource))
        }
        Log.d(TAG, "Being Searched dogs were received in VM.")

    }

    fun addDog(dog: Dog) = viewModelScope.launch {
        Log.d(TAG, "Dog ${dog.name} was added to liked dogs.")
        likedDogsUseCase.likeDog(dog)
    }

    fun getLikedDogs(): LiveData<Resource<MutableList<Dog>>> {
        Log.d(TAG, "Liked dogs were received in VM.")
        val likedDogs = likedDogsUseCase.getLikedDogs()
        val resource = Resource.success(likedDogs)
        likedDogsLivaData.value = resource
        return likedDogsLivaData
    }

    fun deleteDog(dog: Dog) = viewModelScope.launch {
        Log.d(TAG, "Dog ${dog.name} was deleted from liked dogs.")
        likedDogsUseCase.deleteDog(dog)
    }

    private fun handleDogResponse(resource: Resource<MutableList<Dog>>): Resource<MutableList<Dog>> {
        Log.d(TAG, "Handling DogsResponse.")
        if (resource.status == Status.SUCCESS) {
            resource.data?.let { resultDogs ->
                allDogsPage++
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
        allDogsLivaData.postValue(Resource.loading(null))

        val allDogsFlow = allDogsUseCase.getAllDogs()
        allDogsFlow.collect { allDogs ->
            val resource: Resource<MutableList<Dog>> =
                if (allDogs != null) {
                    Resource.success(allDogs)
                } else {
                    Resource.error(null, "No Internet")
                }
            allDogsLivaData.postValue(handleDogResponseWithUpdate(resource))
        }
        Log.d(TAG, "All dogs were updated in VM.")
    }

    private fun handleDogResponseWithUpdate(resource: Resource<MutableList<Dog>>): Resource<MutableList<Dog>> {
        Log.d(TAG, "Handling DogsResponse with update.")
        if (resource.status == Status.SUCCESS) {
            resource.data?.let { resultDogs ->
                allDogsPage++
                dogs = resultDogs
                return Resource.success(dogs)
            }
        }
        Log.d(TAG, "Updated DogsResponse processed.")
        return Resource.error(null, "No Internet")
    }

    private fun handleSearchDogResponse(resource: Resource<MutableList<Dog>>): Resource<MutableList<Dog>> {
        Log.d(TAG, "Handling Search DogsResponse.")
        if (resource.status == Status.SUCCESS) {
            resource.data?.let { resultDogs ->
                searchDogsPage++
                if (searchDogs == null) {
                    searchDogs = resultDogs
                } else {
                    val oldDogs = searchDogs
                    oldDogs?.addAll(resultDogs)
                }
                return Resource.success(dogs ?: resultDogs)
            }
        }
        Log.d(TAG, "Search DogsResponse processed.")
        return Resource.error(null, "No Internet")
    }
}