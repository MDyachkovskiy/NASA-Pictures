package com.test.application.remote_data.repository

import com.test.application.core.repository.MarsPictureRepository
import com.test.application.core.utils.AppState
import com.test.application.remote_data.api.PictureOfTheDayAPI
import com.test.application.remote_data.maper.toDomain
import com.test.application.remote_data.utils.NULL_BODY_ERROR
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MarsPictureRepositoryImpl (
    private val marsPictureApi: PictureOfTheDayAPI
) : MarsPictureRepository {

    override fun getPictureOfMars(camera: String?): Flow<AppState> = flow {
        emit(AppState.Loading)
        try {
            val response = marsPictureApi.getPicturesOfMars(camera = camera)
            if(response.isSuccessful) {
                response.body()?.let { marsPictureResponse ->
                    emit(AppState.Success(marsPictureResponse.toDomain()))
                } ?: emit(AppState.Error(Throwable(NULL_BODY_ERROR)))
            } else {
                emit(AppState.Error(Throwable(response.message())))
            }
        } catch (e:Exception) {
            emit(AppState.Error(e))
        }
    }
}