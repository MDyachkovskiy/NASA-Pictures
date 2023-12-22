package com.test.application.earth_picture.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.test.application.core.domain.earthPicture.EarthPicture
import com.test.application.core.domain.earthPicture.EarthPictureItem
import com.test.application.earth_picture.R
import com.test.application.earth_picture.databinding.ViewPagerCustomTabEarthBinding

class EarthTabsConfigurer(
    private val tabLayout: TabLayout,
    private val viewPager: ViewPager2,
    private val context: Context
) {
    fun setupTabs(pictures: EarthPicture) {
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            val picture = pictures[position]
            tab.customView = createTabView(picture)
        }.attach()
    }

    private fun createTabView(picture: EarthPictureItem): View {
        val layoutInflater = LayoutInflater.from(context)
        val binding = ViewPagerCustomTabEarthBinding
            .inflate(layoutInflater, null, false)

        configureTabView(binding, picture)

        return binding.root
    }

    private fun configureTabView(
        binding: ViewPagerCustomTabEarthBinding,
        picture: EarthPictureItem
    ) {
        with(binding) {
            tabImageTextview.text =
                picture.date?.substring(11, 19) ?: context.getString(R.string.tab_layout_sometime)
            val color = ContextCompat.getColor(context, R.color.main_text_color)
            tabImageTextview.setTextColor(color)

            val icon = ContextCompat.getDrawable(context, R.drawable.ic_earth)
            icon?.setTint(color)

            tabImageTextview
                .setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null)
        }
    }
}