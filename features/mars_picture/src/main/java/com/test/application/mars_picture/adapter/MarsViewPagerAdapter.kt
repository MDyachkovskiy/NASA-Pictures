package com.test.application.mars_picture.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.test.application.core.domain.marsPicture.MarsPhoto

class MarsViewPagerAdapter (
    fragmentActivity: FragmentActivity,
    private val marsPictures: List<MarsPhoto>
    ) : FragmentStateAdapter (fragmentActivity) {

    override fun getItemCount(): Int = marsPictures.size

    override fun createFragment(position: Int): Fragment {
        val marsPicture = marsPictures[position]
        return MarsPictureFragment.newInstance(marsPicture)
    }
}