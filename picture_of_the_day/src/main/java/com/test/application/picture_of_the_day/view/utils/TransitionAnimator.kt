package com.test.application.picture_of_the_day.view.utils

import android.transition.Fade
import android.transition.Slide
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.view.Gravity
import android.view.ViewGroup
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputLayout

class TransitionAnimator {

    fun applyTransitionOnChips(chipGroup: ChipGroup) {
        val chipTransition = TransitionSet().apply {
            duration = 1500
            ordering = TransitionSet.ORDERING_SEQUENTIAL
            addTransition(Slide(Gravity.END))
        }
        TransitionManager.beginDelayedTransition(chipGroup, chipTransition)
    }

    fun applyTransitionOnInputLayout(inputLayout: TextInputLayout) {
        val textInputTransition = TransitionSet().apply {
            duration = 2000
            addTransition(Fade())
        }
        TransitionManager.beginDelayedTransition(inputLayout, textInputTransition)
    }

    fun applyTransitionOnViewGroup(viewGroup: ViewGroup) {
        TransitionManager.beginDelayedTransition(viewGroup)
    }
}