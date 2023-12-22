package com.test.application.earth_picture.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.test.application.core.domain.earthPicture.EarthPictureItem

class ViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val earthPictures: ArrayList<EarthPictureItem>
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = earthPictures.size

    override fun createFragment(position: Int): Fragment {
        val picture = earthPictures[position]
        return EarthPictureFragment.newInstance(picture)
    }
}