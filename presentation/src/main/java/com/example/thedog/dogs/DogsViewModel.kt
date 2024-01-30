package com.example.thedog.dogs


//import dagger.hilt.android.lifecycle.HiltViewModel
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.data.repository.DogRepositoryImpl
import com.example.data.util.Resource
import com.example.domain.model.DogResponse
import com.example.domain.model.DogResponseItem
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.Response

//import javax.inject.Inject

//@HiltViewModel
class DogsViewModel
//@Inject
constructor(app: Application, private val dogRepository: DogRepositoryImpl) : AndroidViewModel(app) {
    val dogs: MutableLiveData<Resource<DogResponse>> = MutableLiveData()
    private var page = 1
    private var dogsResponse: DogResponse? = null

    init {
        getDogs()
    }
    fun getDogs() = viewModelScope.launch {
        dogsInternet()
    }

    private fun handleDogsResponse(response: Response<DogResponse>): Resource<DogResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                page++
                if (dogsResponse == null) {
                    dogsResponse = resultResponse
                }
                return Resource.Success(dogsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun addToLikedDogs(dogResponseItem: DogResponseItem) = viewModelScope.launch {
        dogRepository.upsert(dogResponseItem)
    }

    fun getLikedDogs() = dogRepository.getLikedDogs()

    fun deleteDog(dogResponseItem: DogResponseItem) = viewModelScope.launch {
        dogRepository.deleteDog(dogResponseItem)
    }

    fun internetConnection(context: Context): Boolean {
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).apply {
            return getNetworkCapabilities(activeNetwork)?.run {
                when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            } ?: false
        }
    }

    private suspend fun dogsInternet() {
        dogs.postValue(Resource.Loading())
        try {
            if (internetConnection(this.getApplication())) {
                val response = dogRepository.getDogList()
                dogs.postValue(handleDogsResponse(response))
            } else {
                dogs.postValue(Resource.Error("No Internet connection."))
            }
        } catch (t: Throwable) {
            when(t) {
                is IOException -> dogs.postValue(Resource.Error("Unable to connect."))
                else -> dogs.postValue(Resource.Error("No signal."))
            }
        }
    }


}