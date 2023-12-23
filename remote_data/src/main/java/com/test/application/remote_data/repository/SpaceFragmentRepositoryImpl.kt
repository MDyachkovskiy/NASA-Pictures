package com.test.application.remote_data.repository

import com.test.application.core.repository.SpaceFragmentRepository
import com.test.application.core.utils.AppState
import com.test.application.remote_data.api.PictureOfTheDayAPI
import com.test.application.remote_data.maper.toDomain
import com.test.application.remote_data.utils.NULL_BODY_ERROR
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SpaceFragmentRepositoryImpl(
    private val asteroidsListApi: PictureOfTheDayAPI
) : SpaceFragmentRepository {

    override fun getAsteroidsList(date: String?): Flow<AppState> = flow {
        emit(AppState.Loading)
        try {
            val response = asteroidsListApi.getAsteroidsList(date, date)
            if(response.isSuccessful) {
                response.body()?.let { asteroidListResponse ->
                    emit(AppState.Success(asteroidListResponse.toDomain()))
                } ?: emit(AppState.Error(Throwable(NULL_BODY_ERROR)))
            } else {
                emit(AppState.Error(Throwable(response.message())))
            }
        } catch (e:Exception) {
            emit(AppState.Error(e))
        }
    }
}