package com.gb_materialdesign.model.asteroids


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Kilometers(
    @SerializedName("estimated_diameter_max")
    val estimatedDiameterMax: Double = 0.0,
    @SerializedName("estimated_diameter_min")
    val estimatedDiameterMin: Double = 0.0
) : Parcelable