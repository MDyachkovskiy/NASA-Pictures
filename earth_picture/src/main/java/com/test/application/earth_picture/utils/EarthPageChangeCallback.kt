package com.test.application.earth_picture.utils

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.test.application.earth_picture.R

class EarthPageChangeCallback(
    private val tabLayout: TabLayout,
    private val context: Context
) : ViewPager2.OnPageChangeCallback() {
    override fun onPageSelected(position: Int) {
        val tabCount = tabLayout.tabCount
        for (i in 0 until tabCount) {
            val selectedTab = tabLayout.getTabAt(i)
            val customTabView = selectedTab?.customView as? AppCompatTextView
            val icon = ContextCompat.getDrawable(context, R.drawable.ic_earth)?.mutate()

            if (i == position) {
                updateSelectedTab(customTabView, icon)
            } else {
                updateDeselectedTab(customTabView, icon)
            }
        }
    }

    private fun updateSelectedTab(customTabView: AppCompatTextView?, icon: Drawable?) {
        val color = ContextCompat.getColor(context, R.color.attention_text_color)
        icon?.setTint(color)
        customTabView?.apply {
            setTextColor(color)
            setTypeface(null, Typeface.BOLD)
            setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null)
        }
    }

    private fun updateDeselectedTab(customTabView: AppCompatTextView?, icon: Drawable?) {
        val normalColor = ContextCompat.getColor(context, R.color.main_text_color)
        icon?.setTint(normalColor)
        customTabView?.apply {
            setTextColor(normalColor)
            setTypeface(null, Typeface.NORMAL)
            setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null)
        }
    }
}