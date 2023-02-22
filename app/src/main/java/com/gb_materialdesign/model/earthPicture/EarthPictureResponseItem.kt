package com.gb_materialdesign.model.earthPicture


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class EarthPictureResponseItem(
    val caption: String? = "",
    @SerializedName("centroid_coordinates")
    val centroidCoordinates: CentroidCoordinates? = CentroidCoordinates(),
    val date: String? = "",
    val image: String? = "",
) : Parcelable