package com.gb_materialdesign.model.asteroids


import com.google.gson.annotations.SerializedName

data class CloseApproachData(
    @SerializedName("close_approach_date")
    val closeApproachDate: String,
    @SerializedName("close_approach_date_full")
    val closeApproachDateFull: String,
    @SerializedName("epoch_date_close_approach")
    val epochDateCloseApproach: Long,
    @SerializedName("miss_distance")
    val missDistance: MissDistance,
    @SerializedName("orbiting_body")
    val orbitingBody: String,
    @SerializedName("relative_velocity")
    val relativeVelocity: RelativeVelocity
)