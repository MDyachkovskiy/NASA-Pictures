package com.gb_materialdesign.model.asteroids


import com.google.gson.annotations.SerializedName

data class Asteroid(

    @SerializedName("close_approach_data")
    val closeApproachData: List<CloseApproachData>,

    @SerializedName("estimated_diameter")
    val estimatedDiameter: EstimatedDiameter,

    val id: String,

    @SerializedName("is_potentially_hazardous_asteroid")
    val isPotentiallyHazardousAsteroid: Boolean,

    @SerializedName("is_sentry_object")
    val isSentryObject: Boolean,

    val name: String
)