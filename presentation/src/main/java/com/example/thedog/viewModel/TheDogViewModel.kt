package com.example.thedog.viewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.DogListRepository
//import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
//import javax.inject.Inject

//@HiltViewModel
class TheDogViewModel
//@Inject
constructor(
    private val dogListRepository: DogListRepository
) : ViewModel() {
    init {
        viewModelScope.launch {
            dogListRepository.getDogList()
        }
    }
}