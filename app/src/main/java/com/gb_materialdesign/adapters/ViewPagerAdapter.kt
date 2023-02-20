package com.gb_materialdesign.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gb_materialdesign.ui.main.earth.EarthDayBeforeYesterdayFragment
import com.gb_materialdesign.ui.main.earth.EarthTodayFragment
import com.gb_materialdesign.ui.main.earth.EarthYesterdayFragment

class ViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    val context: Context?
): FragmentStateAdapter(fragmentActivity) {

    private val fragments = arrayOf (EarthTodayFragment(), EarthYesterdayFragment(),
            EarthDayBeforeYesterdayFragment())

    companion object {
        private const val EARTH_TODAY = 0
        private const val EARTH_YESTERDAY = 1
        private const val EARTH_DAY_BEFORE_YESTERDAY = 2
    }

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> fragments[EARTH_TODAY]
            1 -> fragments[EARTH_YESTERDAY]
            2 -> fragments[EARTH_DAY_BEFORE_YESTERDAY]
            else -> fragments[EARTH_TODAY]
        }
    }
}