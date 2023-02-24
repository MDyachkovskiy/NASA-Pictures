package com.gb_materialdesign.ui.main.appState

import com.gb_materialdesign.model.PictureOfTheDayResponse

sealed class AppState {

    object Loading : AppState()

    data class Error(val error: Throwable) : AppState()

    data class Success (val pictureOfTheDay: PictureOfTheDayResponse) : AppState()
}