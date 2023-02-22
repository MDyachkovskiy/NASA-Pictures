package com.gb_materialdesign.repository.earthPicture

import com.gb_materialdesign.model.earthPicture.EarthPictureResponse

interface EarthPictureRepository {
    fun getPicturesOfEarth (date: String, callback: retrofit2.Callback<EarthPictureResponse>)
}