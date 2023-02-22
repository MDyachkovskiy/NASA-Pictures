package com.gb_materialdesign.repository.earthPicture

import com.gb_materialdesign.model.earthPicture.EarthPictureResponse
import com.gb_materialdesign.model.pictureOfTheDay.RemoteSourceNasaAPI
import retrofit2.Callback

class EarthPictureRepositoryImpl (
    private val remoteSourceNasaAPI: RemoteSourceNasaAPI
    ): EarthPictureRepository {

    override fun getPicturesOfEarth(date: String, callback: Callback<EarthPictureResponse>) {
        remoteSourceNasaAPI.getPicturesOfEarthByDate(date, callback)
    }
}