package com.test.application.core.domain.marsPicture

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarsPhoto(
    val camera: MarsCamera = MarsCamera(),
    val earthDate: String = "",
    val id: Int = 0,
    val imgSrc: String = "",
    val rover: Rover = Rover(),
    val sol: Int = 0
) : Parcelable