package com.test.application.remote_data.dto.asteroidList

import com.google.gson.annotations.SerializedName

data class AsteroidsListResponse(
    @SerializedName("element_count")
    val elementCount: Int,

    @SerializedName("near_earth_objects")
    val nearEarthObjects: NearEarthObjectsResponse
)