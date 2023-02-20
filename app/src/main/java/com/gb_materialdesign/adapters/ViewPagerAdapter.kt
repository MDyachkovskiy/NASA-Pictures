package com.gb_materialdesign.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.gb_materialdesign.ui.main.earth.EarthDayBeforeYesterdayFragment
import com.gb_materialdesign.ui.main.earth.EarthTodayFragment
import com.gb_materialdesign.ui.main.earth.EarthYesterdayFragment

class ViewPagerAdapter(fragmentManager: FragmentManager):
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        private val fragments = arrayOf (EarthTodayFragment(), EarthYesterdayFragment(),
            EarthDayBeforeYesterdayFragment())

    companion object {
        private const val EARTH_TODAY = 0
        private const val EARTH_YESTERDAY = 1
        private const val EARTH_DAY_BEFORE_YESTERDAY = 2
    }

    override fun getCount(): Int = fragments.size


    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> fragments[EARTH_TODAY]
            1 -> fragments[EARTH_YESTERDAY]
            2 -> fragments[EARTH_DAY_BEFORE_YESTERDAY]
            else -> fragments[EARTH_TODAY]
        }
    }
}