package com.gb_materialdesign.model.pictureOfTheDay

import com.gb_materialdesign.model.marsPicture.MarsPictureResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val END_POINT = "planetary/apod"

private const val MARS_END_POINT = "mars-photos/api/v1/rovers/curiosity/photos"

interface PictureOfTheDayAPI {

    @GET(END_POINT)
    fun getPictureOfTheDay(
        @Query("api_key") apiKey:String
    ) : Call<PictureOfTheDayResponse>

    @GET(END_POINT)
    fun getPictureOfTheDayByDate(
        @Query("api_key") apiKey:String,
        @Query("date") date: String
    ) : Call<PictureOfTheDayResponse>

    @GET(MARS_END_POINT)
    fun getPicturesOfMars(
        @Query("api_key") apiKey:String,
        @Query("earth_date") date:String,
        @Query("camera") camera:String
    ) : Call<MarsPictureResponse>
}