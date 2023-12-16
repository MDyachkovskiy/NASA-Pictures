package com.gb_materialdesign.repository.earthPicture

import com.test.application.remote_data.dto.earthPictureResponse.EarthPictureResponse

interface EarthPictureRepository {
    fun getPicturesOfEarth (date: String, callback: retrofit2.Callback<com.test.application.remote_data.dto.earthPictureResponse.EarthPictureResponse>)
}