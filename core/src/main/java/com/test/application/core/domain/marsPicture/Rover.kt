package com.test.application.core.domain.marsPicture

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rover(
    val id: Int = 0,
    val landingDate: String = "",
    val launchDate: String = "",
    val name: String = "",
    val status: String = ""
) : Parcelable