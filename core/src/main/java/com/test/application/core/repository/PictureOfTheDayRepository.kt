package com.test.application.core.repository

import com.test.application.core.utils.AppState
import java.util.concurrent.Flow

interface PictureOfTheDayRepository {
    fun getPictureOfTheDay(): Flow<AppState>

    fun getPictureOfTheDayByDate() : Flow<AppState>
}