package com.test.application.core.repository

import com.test.application.core.utils.AppState
import kotlinx.coroutines.flow.Flow

interface EarthPictureRepository {
    fun getPicturesOfEarth (date: String): Flow<AppState>
}