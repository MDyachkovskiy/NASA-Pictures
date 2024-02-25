package com.test.application.picture_of_the_day.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.application.core.repository.PictureOfTheDayRepository
import com.test.application.core.utils.AppState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PictureOfTheDayViewModel(
    private val pictureOfTheDayRepository: PictureOfTheDayRepository
) : ViewModel() {

    private val _stateFlow = MutableStateFlow<AppState>(AppState.Loading)
    val stateFlow: StateFlow<AppState> = _stateFlow.asStateFlow()

   fun getPictureOfTheDay() {
       viewModelScope.launch {
           pictureOfTheDayRepository.getPictureOfTheDay().collect {
               _stateFlow.value = it
           }
       }
    }

    fun getPictureOfTheDayByDate(date: String) {
        viewModelScope.launch {
            pictureOfTheDayRepository.getPictureOfTheDayByDate(date).collect {
                _stateFlow.value = it
            }
        }
    }
}