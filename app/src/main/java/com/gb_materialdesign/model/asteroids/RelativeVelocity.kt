package com.gb_materialdesign.model.asteroids


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RelativeVelocity(
    @SerializedName("kilometers_per_second")
    val kilometersPerSecond: String
) : Parcelable