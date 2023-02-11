package com.gb_materialdesign.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val END_POINT = "planetary/apod"

interface PictureOfTheDayAPI {

    @GET(END_POINT)
    fun getPictureOfTheDay(
        @Query("api_key") apiKey:String
    ) : Call<PictureOfTheDayResponse>
}