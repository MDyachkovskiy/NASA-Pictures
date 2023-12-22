package com.test.application.core.domain.earthPicture

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EarthPictureItem(
    val caption: String? = "",
    val centroidCoordinates: CentroidCoordinates? = CentroidCoordinates(),
    val date: String? = "",
    val image: String? = "",
) : Parcelable