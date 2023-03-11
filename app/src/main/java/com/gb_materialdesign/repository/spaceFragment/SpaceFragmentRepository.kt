package com.gb_materialdesign.repository.spaceFragment

import com.gb_materialdesign.model.asteroids.AsteroidsListResponse

interface SpaceFragmentRepository {
    fun getAsteroidsList (date: String?, callback: retrofit2.Callback<AsteroidsListResponse>)
}