package com.test.application.remote_data.dto.marsPictureResponse


import com.google.gson.annotations.SerializedName

data class Rover(
    val id: Int = 0,
    @SerializedName("landing_date")
    val landingDate: String = "",
    @SerializedName("launch_date")
    val launchDate: String = "",
    val name: String = "",
    val status: String = ""
)