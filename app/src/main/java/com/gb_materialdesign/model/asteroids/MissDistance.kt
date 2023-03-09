package com.gb_materialdesign.model.asteroids


import com.google.gson.annotations.SerializedName

data class MissDistance(
    @SerializedName("astronomical")
    val astronomical: String,
    @SerializedName("kilometers")
    val kilometers: String,
    @SerializedName("lunar")
    val lunar: String
)