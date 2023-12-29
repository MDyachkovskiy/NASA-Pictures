package com.test.application.remote_data.api

import com.test.application.remote_data.dto.asteroidList.AsteroidsListResponse
import com.test.application.remote_data.dto.marsPictureResponse.MarsPictureResponse
import com.test.application.remote_data.dto.pictureOfTheDay.PictureOfTheDayResponse
import com.test.application.remote_data.utils.ASTEROIDS_END_POINT
import com.test.application.remote_data.utils.END_POINT
import com.test.application.remote_data.utils.MARS_END_POINT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureOfTheDayAPI {
    @GET(END_POINT)
    suspend fun getPictureOfTheDay(
    ) : Response<PictureOfTheDayResponse>

    @GET(END_POINT)
    suspend fun getPictureOfTheDayByDate(
        @Query("date") date: String
    ) : Response<PictureOfTheDayResponse>

    @GET(MARS_END_POINT)
    suspend fun getPicturesOfMars(
        @Query("sol") date:String = "1000",
        @Query("camera") camera:String?
    ) : Response<MarsPictureResponse>

    @GET(ASTEROIDS_END_POINT)
    suspend fun getAsteroidsList(
        @Query("start_date") startDate:String?,
        @Query("end_date") endDate:String?
    ) : Response<AsteroidsListResponse>
}