package com.test.application.picture_of_the_day.utils

import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation

class ImageScaleAnimator {
    fun animateScale(
        view: View, initialScale: Float, finalScale: Float,
        pivotX: Float, pivotY: Float, duration: Long
    ) {
        val animation = ScaleAnimation(
            initialScale, finalScale,
            initialScale, finalScale,
            Animation.RELATIVE_TO_SELF, pivotX,
            Animation.RELATIVE_TO_SELF, pivotY
        )
        animation.duration = duration
        animation.fillAfter = true
        view.startAnimation(animation)
    }
}