package com.gb_materialdesign.repository.marsPicture

import com.test.application.remote_data.dto.marsPictureResponse.MarsPictureResponse

interface MarsPictureRepository {
    fun getPictureOfMars (date: String,
                          camera: String?,
                          callback: retrofit2.Callback<MarsPictureResponse>)
}