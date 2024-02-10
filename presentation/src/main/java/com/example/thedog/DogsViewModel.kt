package com.example.thedog


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.repository.DogLocalRepository
import com.example.data.remote.repository.DogRemoteRepository
import com.example.data.util.Resource
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
    val dogsLivaData = MutableLiveData<Resource<List<MDog>>>()
    private val likedLogsLivaData = MutableLiveData<Resource<List<MDog>>>()

    init {
        getDogs()
    }

    fun getDogs() = viewModelScope.launch {
        val result = remoteRepository.getDogs()
        val resource = Resource.success(result)
        dogsLivaData.postValue(resource)
        Log.d(TAG, "All dogs were received in VM.")
//        dogsInternet()
    }

    fun addToLikedDogs(dog: MDog) = viewModelScope.launch {
        Log.d(TAG, "Dog ${dog.name} was added to liked dogs.")
        localRepository.upsert(dog.toEntityModel())
    }

    fun getLikedDogs(): LiveData<Resource<List<MDog>>> {
        Log.d(TAG, "Liked dogs were received in VM.")
        val result = localRepository.getLikedDogs()
        val resource = Resource.success(result)
        likedLogsLivaData.value = resource
        return dogsLivaData
    }

    fun deleteDog(dog: MDog) = viewModelScope.launch {
        Log.d(TAG, "Dog ${dog.name} was deleted from liked dogs.")
        localRepository.deleteDog(dog.toEntityModel())
    }

//    private fun handleDogResponse(response: Response<DogResponse>): Resource<DogResponse> {
//        if (response.isSuccessful) {
//            response.body()?.let { resultResponse ->
//                page++
//                if (dogResponse == null) {
//                    dogResponse = resultResponse
//                } else {
//                    val oldDogs = dogResponse
//                    val newDogs = resultResponse
//                    oldDogs?.addAll(newDogs)
//                }
//                return Resource.Success(dogResponse ?: resultResponse)
//            }
//        }
//        Log.d(TAG, "Handling DogsResponse.")
//        return Resource.Error(response.message())
//    }

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