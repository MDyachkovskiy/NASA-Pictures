package com.test.application.picture_of_the_day.view.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.test.application.picture_of_the_day.R

class BottomSheetBehavior(context: Context, attrs: AttributeSet? = null):
    CoordinatorLayout.Behavior<View>(context, attrs) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return (dependency.id == R.id.bottom_sheet_container)
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        if (dependency.id == R.id.bottom_sheet_container){
            child.x = 1575 - dependency.y
        }
        return super.onDependentViewChanged(parent, child, dependency)
    }
}