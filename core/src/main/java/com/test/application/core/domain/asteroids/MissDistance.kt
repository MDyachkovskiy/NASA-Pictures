package com.test.application.core.domain.asteroids

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MissDistance(
    val kilometers: String
) : Parcelable