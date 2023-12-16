package com.test.application.remote_data.repository

import com.test.application.core.domain.PictureOfTheDayResponse
import com.gb_materialdesign.model.pictureOfTheDay.RemoteSourceNasaAPI
import com.test.application.core.repository.PictureOfTheDayRepository
import com.test.application.core.utils.AppState
import kotlinx.coroutines.flow.flow
import retrofit2.Callback
import java.util.concurrent.Flow

class PictureOfTheDayRepositoryImpl (
    private val remoteSourceNasaAPI: RemoteSourceNasaAPI
) : PictureOfTheDayRepository {

    override fun getPictureOfTheDay(): Flow<AppState> = flow {
        emit(AppState.Loading)
        try {
            val response = remoteSourceNasaAPI.getPic
        }
        remoteSourceNasaAPI.getPictureOfTheDay(callback)
    }

    override fun getPictureOfTheDayByDate(
        date: String,
        callback: Callback<com.test.application.core.domain.PictureOfTheDayResponse>
    ) {
        remoteSourceNasaAPI.getPictureOfTheDayByDate(date, callback)
    }


}