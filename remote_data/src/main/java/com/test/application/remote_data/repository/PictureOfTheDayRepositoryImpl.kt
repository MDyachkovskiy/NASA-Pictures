package com.test.application.remote_data.repository

import com.test.application.core.repository.PictureOfTheDayRepository
import com.test.application.core.utils.AppState
import com.test.application.remote_data.api.PictureOfTheDayAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PictureOfTheDayRepositoryImpl (
    private val pictureOfTheDayAPI: PictureOfTheDayAPI
) : PictureOfTheDayRepository {

    override fun getPictureOfTheDay(): Flow<AppState> = flow {
        emit(AppState.Loading)
        try {
            val response = pictureOfTheDayAPI.getPictureOfTheDay()
            if(response.isSuccessful && response.body() != null){
                emit(AppState.Success(response.body()))
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
            if(response.isSuccessful && response.body() != null){
                emit(AppState.Success(response.body()))
            } else {
                emit(AppState.Error(Throwable(response.message())))
            }
        } catch (e: Exception){
            emit(AppState.Error(e))
        }
    }
}