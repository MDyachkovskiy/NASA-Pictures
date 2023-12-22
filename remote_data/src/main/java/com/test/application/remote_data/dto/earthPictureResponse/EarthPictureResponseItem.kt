package com.test.application.remote_data.dto.earthPictureResponse

import com.google.gson.annotations.SerializedName

data class EarthPictureResponseItem(
    val caption: String? = "",
    @SerializedName("centroid_coordinates")
    val centroidCoordinates: CentroidCoordinatesResponse? = CentroidCoordinatesResponse(),
    val date: String? = "",
    val image: String? = "",
)