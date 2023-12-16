package com.gb_materialdesign.repository.earthPicture

import com.test.application.remote_data.dto.earthPictureResponse.EarthPictureResponse
import com.gb_materialdesign.model.pictureOfTheDay.RemoteSourceNasaAPI
import retrofit2.Callback

class EarthPictureRepositoryImpl (
    private val remoteSourceNasaAPI: RemoteSourceNasaAPI
    ): EarthPictureRepository {

    override fun getPicturesOfEarth(date: String, callback: Callback<com.test.application.remote_data.dto.earthPictureResponse.EarthPictureResponse>) {
        remoteSourceNasaAPI.getPicturesOfEarthByDate(date, callback)
    }
}