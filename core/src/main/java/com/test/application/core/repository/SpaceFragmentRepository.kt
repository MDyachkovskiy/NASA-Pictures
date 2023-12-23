package com.test.application.core.repository

import com.test.application.core.utils.AppState
import kotlinx.coroutines.flow.Flow

interface SpaceFragmentRepository {
    fun getAsteroidsList (date: String?) : Flow<AppState>
}