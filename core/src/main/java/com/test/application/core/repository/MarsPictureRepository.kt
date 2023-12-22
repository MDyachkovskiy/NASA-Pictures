package com.test.application.core.repository

import com.test.application.core.utils.AppState
import kotlinx.coroutines.flow.Flow


interface MarsPictureRepository {
    fun getPictureOfMars (date: String, camera: String?): Flow<AppState>
}