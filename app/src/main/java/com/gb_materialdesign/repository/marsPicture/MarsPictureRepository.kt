package com.gb_materialdesign.repository.marsPicture

import com.gb_materialdesign.model.marsPicture.MarsPictureResponse

interface MarsPictureRepository {
    fun getPictureOfMars (date: String, camera: String, callback: retrofit2.Callback<MarsPictureResponse>)
}