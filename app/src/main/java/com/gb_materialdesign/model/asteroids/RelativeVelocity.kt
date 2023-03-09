package com.gb_materialdesign.model.asteroids


import com.google.gson.annotations.SerializedName

data class RelativeVelocity(
    @SerializedName("kilometers_per_hour")
    val kilometersPerHour: String,
    @SerializedName("kilometers_per_second")
    val kilometersPerSecond: String
)