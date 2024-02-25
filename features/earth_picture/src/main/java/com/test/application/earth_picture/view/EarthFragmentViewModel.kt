package com.test.application.earth_picture.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.application.core.repository.EarthPictureRepository
import com.test.application.core.utils.AppState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EarthFragmentViewModel (
    private val earthPictureRepository: EarthPictureRepository
): ViewModel() {

    private val _stateFlow = MutableStateFlow<AppState>(AppState.Loading)
    val stateFlow: StateFlow<AppState> = _stateFlow.asStateFlow()

    fun getPicturesOfEarth(date: String){
        viewModelScope.launch {
            earthPictureRepository.getPicturesOfEarth(date).collect {
                _stateFlow.value = it
            }
        }
    }
}