package com.gb_materialdesign.model.earthPicture

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface PictureOfEarthAPI {

    @GET("api/enhanced/")
    fun getPicturesOfEarth(
        @Query("date") date:String
    ) : Call<EarthPictureResponse>
}