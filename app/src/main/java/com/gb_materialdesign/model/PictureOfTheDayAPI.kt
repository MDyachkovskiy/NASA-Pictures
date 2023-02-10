package com.gb_materialdesign.model

import androidx.viewbinding.BuildConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val END_POINT = "planetary/apod"

//private const val API_KEY = BuildConfig.NASA_API_KEY

interface PictureOfTheDayAPI {

    @GET(END_POINT)
    fun getPictureOfTheDay(
        @Query("api_key") apiKey:String
    ) : Call<PictureOfTheDayResponse>
}