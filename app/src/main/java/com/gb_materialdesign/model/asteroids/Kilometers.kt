package com.gb_materialdesign.model.asteroids


import com.google.gson.annotations.SerializedName

data class Kilometers(
    @SerializedName("estimated_diameter_max")
    val estimatedDiameterMax: Double,
    @SerializedName("estimated_diameter_min")
    val estimatedDiameterMin: Double
)