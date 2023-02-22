package com.gb_materialdesign.repository.pictureOfTheDay

import com.gb_materialdesign.model.pictureOfTheDay.PictureOfTheDayResponse
import com.gb_materialdesign.model.pictureOfTheDay.RemoteSourceNasaAPI
import retrofit2.Callback

class PictureOfTheDayRepositoryImpl (
    private val remoteSourceNasaAPI: RemoteSourceNasaAPI
    ) : PictureOfTheDayRepository {

    override fun getPictureOfTheDay(callback: Callback<PictureOfTheDayResponse>) {
        remoteSourceNasaAPI.getPictureOfTheDay(callback)
    }

    override fun getPictureOfTheDayByDate(
        date: String,
        callback: Callback<PictureOfTheDayResponse>
    ) {
        remoteSourceNasaAPI.getPictureOfTheDayByDate(date, callback)
    }


}