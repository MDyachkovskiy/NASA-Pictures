package com.gb_materialdesign.di

import com.gb_materialdesign.BuildConfig
import com.gb_materialdesign.utils.EARTH_PICTURE_DOMAIN
import com.gb_materialdesign.utils.NASA_DOMAIN
import com.google.gson.GsonBuilder
import com.test.application.asteroids.view.AsteroidsListViewModel
import com.test.application.contacts.view.ContactsViewModel
import com.test.application.core.repository.ContactsRepository
import com.test.application.core.repository.EarthPictureRepository
import com.test.application.core.repository.MarsPictureRepository
import com.test.application.core.repository.PictureOfTheDayRepository
import com.test.application.core.repository.SpaceFragmentRepository
import com.test.application.earth_picture.view.EarthFragmentViewModel
import com.test.application.mars_picture.view.MarsFragmentViewModel
import com.test.application.picture_of_the_day.view.PictureOfTheDayViewModel
import com.test.application.remote_data.api.EarthPictureAPI
import com.test.application.remote_data.api.PictureOfTheDayAPI
import com.test.application.remote_data.api.UserService
import com.test.application.remote_data.repository.ContactsRepositoryImpl
import com.test.application.remote_data.repository.EarthPictureRepositoryImpl
import com.test.application.remote_data.repository.MarsPictureRepositoryImpl
import com.test.application.remote_data.repository.PictureOfTheDayRepositoryImpl
import com.test.application.remote_data.repository.SpaceFragmentRepositoryImpl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single {
        GsonBuilder()
            .setLenient()
            .create()
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val originalRequest = chain.request()
                val urlWithApiKey = originalRequest.url.newBuilder()
                    .addQueryParameter("api_key", BuildConfig.NASA_API_KEY)
                    .build()
                val newRequest = originalRequest.newBuilder()
                    .url(urlWithApiKey)
                    .build()
                chain.proceed(newRequest)
            })
            .build()
    }

    single(named("nasaRetrofit")) {
        Retrofit.Builder()
            .baseUrl(NASA_DOMAIN)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .client(get())
            .build()
    }

    single(named("earthPictureRetrofit")) {
        Retrofit.Builder()
            .baseUrl(EARTH_PICTURE_DOMAIN)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .client(get())
            .build()
    }

    single { get<Retrofit>(named("nasaRetrofit")).create(PictureOfTheDayAPI::class.java) }
    single { get<Retrofit>(named("earthPictureRetrofit")).create(EarthPictureAPI::class.java) }
}

val repositoryModule = module {
    single { UserService() }

    single<PictureOfTheDayRepository> { PictureOfTheDayRepositoryImpl(get()) }
    single<EarthPictureRepository> { EarthPictureRepositoryImpl(get()) }
    single<MarsPictureRepository> { MarsPictureRepositoryImpl(get()) }
    single<SpaceFragmentRepository> { SpaceFragmentRepositoryImpl(get()) }
    single<ContactsRepository> {ContactsRepositoryImpl(get())}
}

val viewModelModule = module {
    viewModel { PictureOfTheDayViewModel(get()) }
    viewModel { EarthFragmentViewModel(get()) }
    viewModel { MarsFragmentViewModel(get()) }
    viewModel { AsteroidsListViewModel(get()) }
    viewModel { ContactsViewModel(get()) }
}