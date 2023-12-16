package com.test.application.remote_data.dto.asteroidList

import com.google.gson.annotations.SerializedName

data class MissDistance(
    @SerializedName("kilometers")
    val kilometers: String
)