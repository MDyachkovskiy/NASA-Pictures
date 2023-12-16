package com.test.application.remote_data.dto.asteroidList

import com.google.gson.annotations.SerializedName

data class Asteroid(
    @SerializedName("close_approach_data")
    val closeApproachData: List<CloseApproachData> = emptyList(),

    @SerializedName("estimated_diameter")
    val estimatedDiameter: EstimatedDiameter = EstimatedDiameter(),

    val id: String = "",

    @SerializedName("is_potentially_hazardous_asteroid")
    val isPotentiallyHazardousAsteroid: Boolean = false,

    @SerializedName("is_sentry_object")
    val isSentryObject: Boolean = false,

    val name: String = ""
)