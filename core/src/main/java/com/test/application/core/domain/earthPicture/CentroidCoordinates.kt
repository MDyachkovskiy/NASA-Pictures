package com.test.application.core.domain.earthPicture

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CentroidCoordinates(
    val lat: Double? = 0.0,
    val lon: Double? = 0.0
) : Parcelable