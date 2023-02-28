package com.gb_materialdesign.model.marsPicture


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Photo(
    val camera: Camera = Camera(),
    @SerializedName("earth_date")
    val earthDate: String = "",
    val id: Int = 0,
    @SerializedName("img_src")
    val imgSrc: String = "",
    val rover: Rover = Rover(),
    val sol: Int = 0
) : Parcelable