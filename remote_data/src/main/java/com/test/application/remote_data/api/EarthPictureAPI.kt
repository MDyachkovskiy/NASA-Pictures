package com.test.application.remote_data.api

import com.test.application.remote_data.dto.earthPictureResponse.EarthPictureResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface EarthPictureAPI {
    @GET("api/enhanced/")
    suspend fun getPicturesOfEarth(
        @Query("date") date:String
    ) : Response<EarthPictureResponse>
}