package com.gb_materialdesign.model.marsPicture


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rover(
    val id: Int = 0,
    @SerializedName("landing_date")
    val landingDate: String = "",
    @SerializedName("launch_date")
    val launchDate: String = "",
    val name: String = "",
    val status: String = ""
) : Parcelable