package com.test.application.remote_data.dto.marsPictureResponse


import com.google.gson.annotations.SerializedName

data class Photo(
    val camera: Camera = Camera(),
    @SerializedName("earth_date")
    val earthDate: String = "",
    val id: Int = 0,
    @SerializedName("img_src")
    val imgSrc: String = "",
    val rover: Rover = Rover(),
    val sol: Int = 0
)