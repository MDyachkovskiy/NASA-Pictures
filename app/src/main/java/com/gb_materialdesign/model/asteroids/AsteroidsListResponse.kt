package com.gb_materialdesign.model.asteroids


import com.google.gson.annotations.SerializedName

data class AsteroidsListResponse(
    @SerializedName("element_count")
    val elementCount: Int,

    @SerializedName("near_earth_objects")
    val nearEarthObjects: NearEarthObjects
)