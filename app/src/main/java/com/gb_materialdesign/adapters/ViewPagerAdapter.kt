package com.gb_materialdesign.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gb_materialdesign.model.earthPicture.EarthPictureResponseItem
import com.gb_materialdesign.ui.main.earth.EarthPictureFragment

class ViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    val context: Context?,
    private val earthPictures: ArrayList<EarthPictureResponseItem>
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = earthPictures.size

    override fun createFragment(position: Int): Fragment {
        val picture = earthPictures[position]
        return EarthPictureFragment.newInstance(picture)
    }
}