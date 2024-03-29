package com.example.thedog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.util.Resource
import com.example.data.util.Status
import com.example.domain.model.Dog
import com.example.domain.useCases.GetAllDogsUseCase
import com.example.domain.useCases.GetLikedDogsUseCase
import com.example.domain.useCases.GetSearchDogsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val INITIAL_All_DOGS_PAGE = 1
private const val INITIAL_SEARCH_DOGS_PAGE = 1

@HiltViewModel
class DogsViewModel @Inject constructor(
    private val getAllDogsUseCase: GetAllDogsUseCase,
    private val getLikedDogsUseCase: GetLikedDogsUseCase,
    private val getSearchDogsUseCase: GetSearchDogsUseCase
) : ViewModel() {

    private val allDogsLivaData = MutableLiveData<Resource<MutableList<Dog>>>()
    val sharedAllDogsLivaData = allDogsLivaData
    var allDogsPage = INITIAL_All_DOGS_PAGE
    private var dogs: MutableList<Dog>? = null

    private val searchDogsLivaData = MutableLiveData<Resource<MutableList<Dog>>>()
    val sharedSearchDogsLivaData = searchDogsLivaData
    var searchDogsPage = INITIAL_SEARCH_DOGS_PAGE
    private var searchDogs: MutableList<Dog>? = null

    private val likedDogsLivaData = MutableLiveData<Resource<MutableList<Dog>>>()

    init {
        getDogs()
    }

    fun getDogs() = viewModelScope.launch(Dispatchers.IO) {
        allDogsLivaData.postValue(Resource.loading(null))
        val allDogsFlow = getAllDogsUseCase.getAllDogs()
        allDogsFlow.collect { allDogs ->
            val resource = Resource.success(allDogs)
            allDogsLivaData.postValue(handleDogResponse(resource))
        }
    }

    fun searchDogs(searchQuery: String) = viewModelScope.launch(Dispatchers.IO) {
        searchDogsLivaData.postValue(Resource.loading(null))
        val searchDogsFlow = getSearchDogsUseCase.searchDogs(searchQuery)
        searchDogsFlow.collect { searchDogs ->
            val resource = Resource.success(searchDogs)
            searchDogsLivaData.postValue(handleSearchDogResponse(resource))
        }
    }

    fun addDog(dog: Dog) = viewModelScope.launch {
        getLikedDogsUseCase.likeDog(dog)
    }

    fun getLikedDogs(): LiveData<Resource<MutableList<Dog>>> {
        val likedDogs = getLikedDogsUseCase.getLikedDogs()
        val resource = Resource.success(likedDogs)
        likedDogsLivaData.value = resource
        return likedDogsLivaData
    }

    fun deleteDog(dog: Dog) = viewModelScope.launch {
        getLikedDogsUseCase.deleteDog(dog)
    }

    fun updateDogs() = viewModelScope.launch(Dispatchers.IO) {
        allDogsLivaData.postValue(Resource.loading(null))
        val allDogsFlow = getAllDogsUseCase.getAllDogs()
        allDogsFlow.collect { allDogs ->
            val resource: Resource<MutableList<Dog>> =
                if (allDogs != null) {
                    Resource.success(allDogs)
                } else {
                    Resource.error(null, "No Internet")
                }
            allDogsLivaData.postValue(handleDogResponseWithUpdate(resource))
        }
    }

    private fun handleDogResponse(resource: Resource<MutableList<Dog>>): Resource<MutableList<Dog>> {
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
        return Resource.error(null, "No Internet")
    }

    private fun handleDogResponseWithUpdate(resource: Resource<MutableList<Dog>>): Resource<MutableList<Dog>> {
        if (resource.status == Status.SUCCESS) {
            resource.data?.let { resultDogs ->
                allDogsPage++
                dogs = resultDogs
                return Resource.success(dogs)
            }
        }
        return Resource.error(null, "No Internet")
    }

    private fun handleSearchDogResponse(resource: Resource<MutableList<Dog>>): Resource<MutableList<Dog>> {
        if (resource.status == Status.SUCCESS) {
            resource.data?.let { resultDogs ->
                searchDogsPage++
                if (searchDogs == null || searchDogs != resultDogs) {
                    searchDogs = resultDogs
                }
                return Resource.success(searchDogs ?: resultDogs)
            }
        }
        return Resource.error(null, "Not found.")
    }
}