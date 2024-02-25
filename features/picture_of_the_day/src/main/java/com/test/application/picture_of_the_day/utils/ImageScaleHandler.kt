package com.test.application.picture_of_the_day.utils

import android.view.MotionEvent
import com.test.application.core.custom_view.EquilateralImageView

class ImageScaleHandler(
    private val imageView: EquilateralImageView,
    private val animator: ImageScaleAnimator
) {

    private var isImageScaled = false
    private var lastScale = 1f
    private var lastPivotX = 0.5f
    private var lastPivotY = 0.5f

    init {
        setupTouchListener()
    }

    private fun setupTouchListener() {
        imageView.setOnTouchListener { view, motionEvent ->
            if(motionEvent.action == MotionEvent.ACTION_DOWN) {
                view.performClick()
                toggleImageScale(motionEvent)
            }
            true
        }
    }

    private fun toggleImageScale(motionEvent: MotionEvent) {
        if (!isImageScaled) {
            lastPivotX = motionEvent.x / imageView.width
            lastPivotY = motionEvent.y / imageView.height
        }

        val scaleFrom = lastScale
        val scaleTo = if (!isImageScaled) 3f else 1f

        animator.animateScale(imageView, scaleFrom, scaleTo, lastPivotX, lastPivotY, 1000)
        isImageScaled = !isImageScaled
        lastScale = scaleTo
    }
}