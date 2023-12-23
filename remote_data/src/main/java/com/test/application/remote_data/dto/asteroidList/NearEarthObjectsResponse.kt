package com.test.application.remote_data.dto.asteroidList

import com.google.gson.*
import com.google.gson.annotations.SerializedName
import java.util.*

data class NearEarthObjectsResponse(
    @SerializedName("2023-03-10")
    val asteroids: List<AsteroidResponse>
)
