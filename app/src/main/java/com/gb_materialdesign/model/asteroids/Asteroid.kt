package com.gb_materialdesign.model.asteroids


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
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
) : Parcelable