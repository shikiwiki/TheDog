package com.example.thedog


//import dagger.hilt.android.lifecycle.HiltViewModel
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.data.local.entities.toEntityModel
import com.example.data.local.repository.DogLocalRepository
import com.example.data.remote.dto.DogResponse
import com.example.data.remote.repository.DogRemoteRepository
import com.example.data.util.Resource
import com.example.domain.model.MDogResponse
import com.example.domain.model.MDog
import kotlinx.coroutines.launch
import retrofit2.Response

//import javax.inject.Inject
private const val TAG = "DogsViewModel"

//@HiltViewModel
class DogsViewModel
//@Inject
constructor(
    app: Application,
    private val remoteRepository: DogRemoteRepository,
    private val localRepository: DogLocalRepository
) :
    AndroidViewModel(app) {
    val dogs: MutableLiveData<Resource<MDogResponse>> = MutableLiveData()
    var page = 1
    var dogResponse: DogResponse? = null

    val dogsLivaData = MutableLiveData<List<MDog>>()
    val likedLogsLivaData = MutableLiveData<List<MDog>>()

    init {
        getDogs()
    }

    fun getDogs() = viewModelScope.launch {
        val result = remoteRepository.getDogs()
        dogsLivaData.value = result
//        Log.d(TAG, "All dogs were received.")
//        dogsInternet()
    }

    private fun handleDogResponse(response: Response<DogResponse>): Resource<DogResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                page++
                if (dogResponse == null) {
                    dogResponse = resultResponse
                } else {
                    val oldDogs = dogResponse
                    val newDogs = resultResponse
                    oldDogs?.addAll(newDogs)
                }
                return Resource.Success(dogResponse ?: resultResponse)
            }
        }
        Log.d(TAG, "Handling DogsResponse.")
        return Resource.Error(response.message())
    }

    fun addToLikedDogs(dog: MDog) = viewModelScope.launch {
//        Log.d(TAG, "Dog was added to liked dogs.")
        localRepository.upsert(dog.toEntityModel())
    }

    fun getLikedDogs(): LiveData<List<MDog>> {
        Log.d(TAG, "Liked dogs were received.")
        likedLogsLivaData.value = localRepository.getLikedDogs()
        return dogsLivaData
    }

    fun deleteDog(dog: MDog) = viewModelScope.launch {
//        Log.d(TAG, "Dog was deleted from liked dogs.")
        localRepository.deleteDog(dog.toEntityModel())
    }

    private fun internetConnection(context: Context): Boolean {
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).apply {
            return getNetworkCapabilities(activeNetwork)?.run {
                Log.d(TAG, "Internet connection.")
                when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            } ?: false
        }
    }

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