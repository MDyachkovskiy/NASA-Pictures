package com.test.application.core.repository

import com.test.application.core.utils.AppState
import kotlinx.coroutines.flow.Flow

interface PictureOfTheDayRepository {
    fun getPictureOfTheDay(): Flow<AppState>

    fun getPictureOfTheDayByDate(date: String) : Flow<AppState>
}