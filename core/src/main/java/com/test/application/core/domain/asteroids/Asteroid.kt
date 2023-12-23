package com.test.application.core.domain.asteroids

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Asteroid(
    val closeApproachData: List<CloseApproachData> = emptyList(),
    val estimatedDiameter: EstimatedDiameter = EstimatedDiameter(),
    val id: String = "",
    val isPotentiallyHazardousAsteroid: Boolean = false,
    val isSentryObject: Boolean = false,
    val name: String = ""
) : Parcelable