package com.test.application.remote_data.api

import com.test.application.remote_data.dto.asteroidList.AsteroidsListResponse
import com.test.application.remote_data.dto.marsPictureResponse.MarsPictureResponse
import com.test.application.remote_data.dto.pictureOfTheDay.PictureOfTheDayResponse
import com.test.application.remote_data.utils.ASTEROIDS_END_POINT
import com.test.application.remote_data.utils.END_POINT
import com.test.application.remote_data.utils.MARS_END_POINT
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

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
        @Query("camera") camera:String?
    ) : Call<MarsPictureResponse>

    @GET(ASTEROIDS_END_POINT)
    fun getAsteroidsList(
        @Query("api_key") apiKey:String,
        @Query("start_date") startDate:String?,
        @Query("end_date") endDate:String?
    ) : Call<AsteroidsListResponse>
}