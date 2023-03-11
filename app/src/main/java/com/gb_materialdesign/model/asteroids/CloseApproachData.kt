package com.gb_materialdesign.model.asteroids


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CloseApproachData(

    @SerializedName("miss_distance")
    val missDistance: MissDistance,

    @SerializedName("relative_velocity")
    val relativeVelocity: RelativeVelocity
) : Parcelable