package com.test.application.mars_picture.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.application.core.repository.MarsPictureRepository
import com.test.application.core.utils.AppState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MarsFragmentViewModel(
    private val marsPictureRepository: MarsPictureRepository
) : ViewModel() {

    private val _stateFlow = MutableStateFlow<AppState>(AppState.Loading)
    val stateFlow: StateFlow<AppState> = _stateFlow.asStateFlow()

    fun getPicturesOfMarsByCamera (camera: String?, date: String) {
        viewModelScope.launch {
            marsPictureRepository.getPictureOfMars(date, camera).collect {
                _stateFlow.value = it
            }
        }
    }
}