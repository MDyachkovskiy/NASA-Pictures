package com.gb_materialdesign.di

import com.gb_materialdesign.model.pictureOfTheDay.RemoteSourceNasaAPI
import com.gb_materialdesign.utils.NASA_DOMAIN
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.test.application.remote_data.api.EarthPictureAPI
import com.test.application.remote_data.api.PictureOfTheDayAPI
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    fun provideInterceptor() = RemoteSourceNasaAPI.PictureOfTheDayInterceptor()

    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NASA_DOMAIN)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    single { provideGson() }
    single { provideInterceptor() }
    single { provideOkHttpClient(get()) }
    single { provideRetrofit(get(), get()) }

    single { get<Retrofit>().create(PictureOfTheDayAPI::class.java) }
    single { get<Retrofit>().create(EarthPictureAPI::class.java) }
    single { RemoteSourceNasaAPI(get(), get()) }
}