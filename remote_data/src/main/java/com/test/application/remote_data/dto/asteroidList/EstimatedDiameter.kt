package com.test.application.remote_data.dto.asteroidList

import com.google.gson.annotations.SerializedName

data class EstimatedDiameter(

    @SerializedName("kilometers")
    val kilometers: Kilometers = Kilometers(),
)