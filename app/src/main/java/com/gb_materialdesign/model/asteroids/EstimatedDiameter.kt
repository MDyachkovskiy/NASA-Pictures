package com.gb_materialdesign.model.asteroids


import com.google.gson.annotations.SerializedName

data class EstimatedDiameter(

    @SerializedName("kilometers")
    val kilometers: Kilometers
)