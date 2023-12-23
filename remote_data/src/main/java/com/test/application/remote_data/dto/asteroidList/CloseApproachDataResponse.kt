package com.test.application.remote_data.dto.asteroidList

import com.google.gson.annotations.SerializedName

data class CloseApproachDataResponse(

    @SerializedName("miss_distance")
    val missDistance: MissDistanceResponse,

    @SerializedName("relative_velocity")
    val relativeVelocity: RelativeVelocityResponse
)