package com.gb_materialdesign.ui.main.appState

import com.gb_materialdesign.model.earthPicture.EarthPictureResponse
import com.gb_materialdesign.model.pictureOfTheDay.PictureOfTheDayResponse

sealed class AppState {

    object Loading : AppState()

    data class Error(val error: Throwable) : AppState()

    data class SuccessTelescope (val pictureOfTheDay: PictureOfTheDayResponse) : AppState()

    data class SuccessEarthPicture (val earthPictures: EarthPictureResponse) : AppState()
}