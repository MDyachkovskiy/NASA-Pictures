package com.test.application.core.domain.asteroids

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EstimatedDiameter(
    val kilometers: Kilometers = Kilometers(),
) : Parcelable