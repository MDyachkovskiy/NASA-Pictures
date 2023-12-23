package com.test.application.remote_data.dto.asteroidList

import com.google.gson.annotations.SerializedName

data class AsteroidResponse(
    @SerializedName("close_approach_data")
    val closeApproachData: List<CloseApproachDataResponse> = emptyList(),

    @SerializedName("estimated_diameter")
    val estimatedDiameter: EstimatedDiameterResponse = EstimatedDiameterResponse(),

    val id: String = "",

    @SerializedName("is_potentially_hazardous_asteroid")
    val isPotentiallyHazardousAsteroid: Boolean = false,

    @SerializedName("is_sentry_object")
    val isSentryObject: Boolean = false,

    val name: String = ""
)