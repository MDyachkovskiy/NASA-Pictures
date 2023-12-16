package com.gb_materialdesign.model.pictureOfTheDay

import android.util.Log
import com.gb_materialdesign.utils.NASA_API_KEY
import okhttp3.Interceptor
import retrofit2.Callback
import java.io.IOException

class RemoteSourceNasaAPI {

    inner class PictureOfTheDayInterceptor : Interceptor {

        @kotlin.jvm.Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val request = chain.request()
            Log.d("MyInterceptor","Request: ${request.method} ${request.url}")

            val response = chain.proceed(request)
            Log.d("MyInterceptor", "Response: ${response.code} ${response.message}")

            return response
        }
    }

    fun getPictureOfTheDay(callback: Callback<com.test.application.core.domain.PictureOfTheDayResponse>){
        nasaAPI.getPictureOfTheDay(NASA_API_KEY).enqueue(callback)
    }

    fun getPictureOfTheDayByDate (date: String, callback: Callback<com.test.application.core.domain.PictureOfTheDayResponse>){
        nasaAPI.getPictureOfTheDayByDate(NASA_API_KEY, date).enqueue(callback)
    }

    fun getPicturesOfEarthByDate (date: String, callback: Callback<com.test.application.remote_data.dto.earthPictureResponse.EarthPictureResponse>){
        earthPictureAPI.getPicturesOfEarth(date).enqueue(callback)
    }

    fun getPicturesOfMars (date: String, camera: String?, callback: Callback<com.test.application.remote_data.dto.marsPictureResponse.MarsPictureResponse>){
        nasaAPI.getPicturesOfMars(NASA_API_KEY, "2023-02-20", camera).enqueue(callback)
    }

    fun getAsteroidsList (date: String?, callback: Callback<com.test.application.remote_data.dto.asteroidList.AsteroidsListResponse>){
        nasaAPI.getAsteroidsList(NASA_API_KEY, "2023-03-10", date).enqueue(callback)
    }
}