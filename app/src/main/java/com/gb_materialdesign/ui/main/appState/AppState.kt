package com.gb_materialdesign.ui.main.appState

import com.gb_materialdesign.model.asteroids.AsteroidsListResponse
import com.gb_materialdesign.model.earthPicture.EarthPictureResponse
import com.gb_materialdesign.model.marsPicture.MarsPictureResponse
import com.gb_materialdesign.model.pictureOfTheDay.PictureOfTheDayResponse

sealed class AppState {

    object Loading : AppState()

    data class Error(val error: Throwable) : AppState()

    data class SuccessTelescope (val pictureOfTheDay: PictureOfTheDayResponse) : AppState()

    data class SuccessEarthPicture (val earthPictures: EarthPictureResponse) : AppState()

    data class SuccessMarsPicture (val marsPictures: MarsPictureResponse) : AppState()

    data class SuccessAsteroidList (val asteroidList: AsteroidsListResponse) : AppState()
}