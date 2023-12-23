package com.test.application.remote_data.dto.asteroidList

import com.google.gson.annotations.SerializedName

data class EstimatedDiameterResponse(

    @SerializedName("kilometers")
    val kilometers: KilometersResponse = KilometersResponse(),
)