package com.test.application.asteroids

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.application.core.repository.SpaceFragmentRepository
import com.test.application.core.utils.AppState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AsteroidsListViewModel(
    private val spaceFragmentRepository: SpaceFragmentRepository
) :ViewModel() {

    private val _stateFlow = MutableStateFlow<AppState>(AppState.Loading)
    val stateFlow: StateFlow<AppState> = _stateFlow.asStateFlow()

    fun getAsteroidsList(date: String?) {
        viewModelScope.launch {
            spaceFragmentRepository.getAsteroidsList(date).collect {
                _stateFlow.value = it
            }
        }
    }
}