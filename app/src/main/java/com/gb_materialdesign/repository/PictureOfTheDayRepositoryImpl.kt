package com.gb_materialdesign.repository

import com.gb_materialdesign.model.PictureOfTheDayResponse
import com.gb_materialdesign.model.RemoteSourceNasaAPI
import retrofit2.Callback

class PictureOfTheDayRepositoryImpl (
    private val remoteSourceNasaAPI: RemoteSourceNasaAPI
    ) : PictureOfTheDayRepository {

    override fun getPictureOfTheDay(callback: Callback<PictureOfTheDayResponse>) {
        remoteSourceNasaAPI.getPictureOfTheDay(callback)
    }
}