package com.test.application.picture_of_the_day.utils

import android.content.Context
import androidx.core.content.ContextCompat
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.test.application.picture_of_the_day.R

class BottomAppBarConfigurator(
    private var context: Context?
) {

    fun setupBottomAppBar(
        bottomAppBar: BottomAppBar, fab: FloatingActionButton, isMain: Boolean) {

        if (isMain) {
            setupMainState(bottomAppBar, fab)
        } else {
            setupAlternateState(bottomAppBar, fab)
        }
    }

    private fun setupMainState(bottomAppBar: BottomAppBar, fab: FloatingActionButton) {
        bottomAppBar.navigationIcon = context?.let {
            ContextCompat
                .getDrawable(it, com.test.application.core.R.drawable.ic_hamburger_menu_bottom_bar)
        }
        bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
        fab.setImageDrawable(context?.let { ContextCompat.getDrawable(it, R.drawable.ic_plus_fab) })
    }

    private fun setupAlternateState(bottomAppBar: BottomAppBar, fab: FloatingActionButton) {
        bottomAppBar.navigationIcon = null
        bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
        fab.setImageDrawable(context?.let { ContextCompat.getDrawable(it, R.drawable.ic_back_fab) })
        bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
    }

    fun cleanup() {
        context = null
    }
}