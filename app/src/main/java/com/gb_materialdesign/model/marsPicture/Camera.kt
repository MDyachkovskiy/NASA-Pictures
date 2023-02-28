package com.gb_materialdesign.model.marsPicture


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Camera(
    @SerializedName("full_name")
    val fullName: String = "",
    val id: Int = 0,
    val name: String = "",
    @SerializedName("rover_id")
    val roverId: Int = 0
) : Parcelable