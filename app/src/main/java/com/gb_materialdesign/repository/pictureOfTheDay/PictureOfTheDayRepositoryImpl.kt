package com.gb_materialdesign.repository.pictureOfTheDay

import com.test.application.core.domain.PictureOfTheDayResponse
import com.gb_materialdesign.model.pictureOfTheDay.RemoteSourceNasaAPI
import retrofit2.Callback

class PictureOfTheDayRepositoryImpl (
    private val remoteSourceNasaAPI: RemoteSourceNasaAPI
    ) : PictureOfTheDayRepository {

    override fun getPictureOfTheDay(callback: Callback<com.test.application.core.domain.PictureOfTheDayResponse>) {
        remoteSourceNasaAPI.getPictureOfTheDay(callback)
    }

    override fun getPictureOfTheDayByDate(
        date: String,
        callback: Callback<com.test.application.core.domain.PictureOfTheDayResponse>
    ) {
        remoteSourceNasaAPI.getPictureOfTheDayByDate(date, callback)
    }


}