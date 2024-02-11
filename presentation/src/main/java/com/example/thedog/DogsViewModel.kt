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
import com.example.domain.model.MDog
import kotlinx.coroutines.launch

private const val TAG = "DogsViewModel"

class DogsViewModel(
    private val remoteRepository: DogRemoteRepository,
    private val localRepository: DogLocalRepository
) :
    ViewModel() {
    var page = 1
    val dogsLivaData : MutableLiveData<Resource<MutableList<MDog>>> = MutableLiveData()
    private val likedLogsLivaData = MutableLiveData<Resource<MutableList<MDog>>>()
    var dogs : MutableList<MDog>? = null

    init {
        getDogs()
    }

    fun getDogs() = viewModelScope.launch {
        dogsLivaData.postValue(Resource.loading(null))
        val result = remoteRepository.getDogs()
        val resource = Resource.success(result)
//        dogsLivaData.postValue(resource)
        dogsLivaData.postValue(handleDogResponse(resource))
        Log.d(TAG, "All dogs were received in VM.")
    }

    fun addToLikedDogs(dog: MDog) = viewModelScope.launch {
//        likedLogsLivaData.postValue(Resource.loading(null))
        Log.d(TAG, "Dog ${dog.name} was added to liked dogs.")
        localRepository.upsert(dog.toEntityModel())
    }

    fun getLikedDogs(): LiveData<Resource<MutableList<MDog>>> {
        Log.d(TAG, "Liked dogs were received in VM.")
        val result = localRepository.getLikedDogs()
        val resource = Resource.success(result)
        likedLogsLivaData.value = resource
        return likedLogsLivaData
    }

    fun deleteDog(dog: MDog) = viewModelScope.launch {
        Log.d(TAG, "Dog ${dog.name} was deleted from liked dogs.")
        localRepository.deleteDog(dog.toEntityModel())
    }

    private fun handleDogResponse(resource: Resource<MutableList<MDog>>) : Resource<MutableList<MDog>> {
        if (resource.status == Status.SUCCESS) {
            resource.data?.let { resultDogs ->
                page++
                if (dogs == null) {
                    dogs = resultDogs
                } else {
                    val oldDogs = dogs
                    val newDogs = resultDogs
                    oldDogs?.addAll(newDogs)
                }
                return Resource.success(dogs ?: resultDogs)
            }
        }
        Log.d(TAG, "Handling DogsResponse.")
        return Resource.error(null, resource.message.toString())
    }

//    private fun internetConnection(context: Context): Boolean {
//        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).apply {
//            return getNetworkCapabilities(activeNetwork)?.run {
//                Log.d(TAG, "Internet connection.")
//                when {
//                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
//                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
//                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
//                    else -> false
//                }
//            } ?: false
//        }
//    }

//    private suspend fun dogsInternet() {
//        dogs.postValue(Resource.Loading())
//        try {
//            if (internetConnection(this.getApplication())) {
//                val response = remoteRepository.getDogs()
//                dogs.postValue(handleDogResponse(response))
//            } else {
//                dogs.postValue(Resource.Error("No Internet connection."))
//            }
//        } catch (t: Throwable) {
//            when (t) {
//                is IOException -> dogs.postValue(Resource.Error("Unable to connect."))
//                else -> dogs.postValue(Resource.Error("No signal."))
//            }
//        }
//        Log.d(TAG, "DogsInternet is successful.")
//    }


}