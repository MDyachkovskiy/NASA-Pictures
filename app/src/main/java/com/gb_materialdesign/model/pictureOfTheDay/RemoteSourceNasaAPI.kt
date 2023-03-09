package com.gb_materialdesign.model.pictureOfTheDay

import com.gb_materialdesign.model.asteroids.AsteroidsListResponse
import com.gb_materialdesign.model.earthPicture.EarthPictureResponse
import com.gb_materialdesign.model.earthPicture.PictureOfEarthAPI
import com.gb_materialdesign.model.marsPicture.MarsPictureResponse
import com.gb_materialdesign.utils.EARTH_PICTURE_DOMAIN
import com.gb_materialdesign.utils.NASA_API_KEY
import com.gb_materialdesign.utils.NASA_DOMAIN
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class RemoteSourceNasaAPI {

    private val nasaAPI = Retrofit.Builder()
        .baseUrl(NASA_DOMAIN)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .client(createOkHttpClient(PictureOfTheDayInterceptor()))
        .build()
        .create(PictureOfTheDayAPI::class.java)

    private val earthPictureAPI = Retrofit.Builder()
        .baseUrl(EARTH_PICTURE_DOMAIN)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()
        .create(PictureOfEarthAPI::class.java)

    private fun createOkHttpClient(
        interceptor: Interceptor
    ): OkHttpClient {

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor)

        httpClient.addInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY))

        return httpClient.build()
    }

    inner class PictureOfTheDayInterceptor : Interceptor {

        @kotlin.jvm.Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            return chain.proceed(chain.request())
        }
    }

    fun getPictureOfTheDay(callback: Callback<PictureOfTheDayResponse>){
        nasaAPI.getPictureOfTheDay(NASA_API_KEY).enqueue(callback)
    }

    fun getPictureOfTheDayByDate (date: String, callback: Callback<PictureOfTheDayResponse>){
        nasaAPI.getPictureOfTheDayByDate(NASA_API_KEY, date).enqueue(callback)
    }

    fun getPicturesOfEarthByDate (date: String, callback: Callback<EarthPictureResponse>){
        earthPictureAPI.getPicturesOfEarth(date).enqueue(callback)
    }

    fun getPicturesOfMars (date: String, camera: String?, callback: Callback<MarsPictureResponse>){
        nasaAPI.getPicturesOfMars(NASA_API_KEY, "2023-02-20", camera).enqueue(callback)
    }

    fun getAsteroidsList (date: String?, callback: Callback<AsteroidsListResponse>){
        nasaAPI.getAsteroidsList(NASA_API_KEY, date, date).enqueue(callback)
    }

}