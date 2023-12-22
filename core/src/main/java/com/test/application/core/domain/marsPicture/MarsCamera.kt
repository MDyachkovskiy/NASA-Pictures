package com.test.application.core.domain.marsPicture

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarsCamera(
    val fullName: String = "",
    val id: Int = 0,
    val name: String = "",
    val roverId: Int = 0
) : Parcelable