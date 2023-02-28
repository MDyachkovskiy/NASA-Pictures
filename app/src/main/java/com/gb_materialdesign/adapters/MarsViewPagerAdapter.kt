package com.gb_materialdesign.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gb_materialdesign.model.marsPicture.Photo
import com.gb_materialdesign.ui.main.mars.MarsPictureFragment

class MarsViewPagerAdapter (
    fragmentActivity: FragmentActivity,
    val context: Context?,
    private val marsPictures: List<Photo>
    ) : FragmentStateAdapter (fragmentActivity) {

    override fun getItemCount(): Int = marsPictures.size

    override fun createFragment(position: Int): Fragment {
        val marsPicture = marsPictures[position]
        return MarsPictureFragment.newInstance(marsPicture)
    }


}