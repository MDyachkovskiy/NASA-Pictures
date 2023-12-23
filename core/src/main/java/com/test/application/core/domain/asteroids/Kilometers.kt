package com.test.application.core.domain.asteroids

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Kilometers(
    val estimatedDiameterMax: Double = 0.0,
    val estimatedDiameterMin: Double = 0.0
) : Parcelable