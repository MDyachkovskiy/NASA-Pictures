package com.test.application.remote_data.repository

import com.test.application.core.repository.PictureOfTheDayRepository
import com.test.application.core.utils.AppState
import com.test.application.remote_data.api.PictureOfTheDayAPI
import com.test.application.remote_data.maper.toDomain
import com.test.application.remote_data.utils.NULL_BODY_ERROR
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PictureOfTheDayRepositoryImpl (
    private val pictureOfTheDayAPI: PictureOfTheDayAPI
) : PictureOfTheDayRepository {

    override fun getPictureOfTheDay(): Flow<AppState> = flow {
        emit(AppState.Loading)
        try {
            val response = pictureOfTheDayAPI.getPictureOfTheDay()
            if(response.isSuccessful) {
                response.body()?.let { pictureOfTheDayResponse ->
                    emit(AppState.Success(pictureOfTheDayResponse.toDomain()))
                }  ?: emit(AppState.Error(Throwable(NULL_BODY_ERROR)))
            } else {
                emit(AppState.Error(Throwable(response.message())))
            }
        } catch (e: Exception){
            emit(AppState.Error(e))
        }
    }

    override fun getPictureOfTheDayByDate(date: String): Flow<AppState> = flow {
        emit(AppState.Loading)
        try {
            val response = pictureOfTheDayAPI.getPictureOfTheDayByDate(date)
            if(response.isSuccessful) {
                response.body()?.let { pictureOfTheDayResponse ->
                    emit(AppState.Success(pictureOfTheDayResponse.toDomain()))
                }  ?: emit(AppState.Error(Throwable(NULL_BODY_ERROR)))
            } else {
                emit(AppState.Error(Throwable(response.message())))
            }
        } catch (e: Exception){
            emit(AppState.Error(e))
        }
    }
}