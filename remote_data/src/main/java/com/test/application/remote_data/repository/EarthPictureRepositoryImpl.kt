package com.test.application.remote_data.repository

import com.test.application.core.repository.EarthPictureRepository
import com.test.application.core.utils.AppState
import com.test.application.remote_data.api.EarthPictureAPI
import com.test.application.remote_data.maper.toDomain
import com.test.application.remote_data.utils.NULL_BODY_ERROR
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class EarthPictureRepositoryImpl (
    private val earthPictureApi: EarthPictureAPI
): EarthPictureRepository {

    override fun getPicturesOfEarth(date: String): Flow<AppState> = flow {
        emit(AppState.Loading)
        try {
            val response = earthPictureApi.getPicturesOfEarth(date)
            if(response.isSuccessful){
                response.body()?.let { earthPictureResponse ->
                    emit(AppState.Success(earthPictureResponse.toDomain()))
                } ?: emit(AppState.Error(Throwable(NULL_BODY_ERROR)))
            } else {
                emit(AppState.Error(Throwable(response.message())))
            }
        } catch (e: Exception) {
            emit(AppState.Error(e))
        }
    }
}