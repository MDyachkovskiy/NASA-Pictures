package com.gb_materialdesign.model.earthPicture


import com.google.gson.annotations.SerializedName

data class EarthPictureResponseItem(
    val caption: String,
    @SerializedName("centroid_coordinates")
    val centroidCoordinates: CentroidCoordinates,
    val date: String,
    val image: String,
)