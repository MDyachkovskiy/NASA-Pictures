package com.test.application.remote_data.dto.marsPictureResponse

import com.google.gson.annotations.SerializedName

data class CameraResponse(
    @SerializedName("full_name")
    val fullName: String = "",
    val id: Int = 0,
    val name: String = "",
    @SerializedName("rover_id")
    val roverId: Int = 0
)