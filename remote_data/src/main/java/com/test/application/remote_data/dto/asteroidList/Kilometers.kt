package com.test.application.remote_data.dto.asteroidList

import com.google.gson.annotations.SerializedName

data class Kilometers(
    @SerializedName("estimated_diameter_max")
    val estimatedDiameterMax: Double = 0.0,
    @SerializedName("estimated_diameter_min")
    val estimatedDiameterMin: Double = 0.0
)