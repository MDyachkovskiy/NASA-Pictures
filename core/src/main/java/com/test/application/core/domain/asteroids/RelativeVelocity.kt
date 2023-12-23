package com.test.application.core.domain.asteroids

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RelativeVelocity(
    val kilometersPerSecond: String
) : Parcelable