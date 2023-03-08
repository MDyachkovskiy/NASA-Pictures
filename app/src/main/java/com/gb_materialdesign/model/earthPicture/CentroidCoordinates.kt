package com.gb_materialdesign.model.earthPicture

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class CentroidCoordinates(
    val lat: Double? = 0.0,
    val lon: Double? = 0.0
) : Parcelable