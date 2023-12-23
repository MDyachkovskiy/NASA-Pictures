package com.test.application.remote_data.dto.asteroidList

import com.google.gson.annotations.SerializedName

data class RelativeVelocityResponse(
    @SerializedName("kilometers_per_second")
    val kilometersPerSecond: String
)