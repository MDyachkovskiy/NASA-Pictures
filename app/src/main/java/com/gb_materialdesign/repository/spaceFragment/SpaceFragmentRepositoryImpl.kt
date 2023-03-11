package com.gb_materialdesign.repository.spaceFragment

import com.gb_materialdesign.model.asteroids.AsteroidsListResponse
import com.gb_materialdesign.model.pictureOfTheDay.RemoteSourceNasaAPI
import retrofit2.Callback

class SpaceFragmentRepositoryImpl(
    private val remoteSourceNasaAPI: RemoteSourceNasaAPI
) : SpaceFragmentRepository {

    override fun getAsteroidsList(date: String?, callback: Callback<AsteroidsListResponse>) {
        remoteSourceNasaAPI.getAsteroidsList(date, callback)
    }
}