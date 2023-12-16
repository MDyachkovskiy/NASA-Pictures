package com.test.application.remote_data.api

import com.test.application.remote_data.dto.earthPictureResponse.EarthPictureResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EarthPictureAPI {
    @GET("api/enhanced/")
    fun getPicturesOfEarth(
        @Query("date") date:String
    ) : Call<EarthPictureResponse>
}