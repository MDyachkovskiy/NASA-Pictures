package com.gb_materialdesign.repository.spaceFragment

import com.test.application.remote_data.dto.asteroidList.AsteroidsListResponse
import com.gb_materialdesign.model.pictureOfTheDay.RemoteSourceNasaAPI
import retrofit2.Callback

class SpaceFragmentRepositoryImpl(
    private val remoteSourceNasaAPI: RemoteSourceNasaAPI
) : SpaceFragmentRepository {

    override fun getAsteroidsList(date: String?, callback: Callback<com.test.application.remote_data.dto.asteroidList.AsteroidsListResponse>) {
        remoteSourceNasaAPI.getAsteroidsList(date, callback)
    }
}