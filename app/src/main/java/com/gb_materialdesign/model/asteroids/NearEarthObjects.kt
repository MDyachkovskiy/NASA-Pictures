package com.gb_materialdesign.model.asteroids

import com.google.gson.*
import com.google.gson.annotations.SerializedName
import java.util.*

data class NearEarthObjects(
    @SerializedName("2023-03-10")
    val asteroids: List<Asteroid>
)
