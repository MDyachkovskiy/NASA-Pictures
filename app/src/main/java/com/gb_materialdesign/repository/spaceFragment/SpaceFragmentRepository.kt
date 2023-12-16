package com.gb_materialdesign.repository.spaceFragment

import com.test.application.remote_data.dto.asteroidList.AsteroidsListResponse

interface SpaceFragmentRepository {
    fun getAsteroidsList (date: String?, callback: retrofit2.Callback<com.test.application.remote_data.dto.asteroidList.AsteroidsListResponse>)
}