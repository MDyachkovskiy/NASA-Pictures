package com.gb_materialdesign.ui.main.earth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gb_materialdesign.R
import com.gb_materialdesign.adapters.ViewPagerAdapter
import com.gb_materialdesign.databinding.FragmentEarthBinding
import com.google.android.material.tabs.TabLayoutMediator

class EarthFragment : Fragment() {

    private var _binding: FragmentEarthBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = EarthFragment()
        private const val EARTH_TODAY = 0
        private const val EARTH_YESTERDAY = 1
        private const val EARTH_DAY_BEFORE_YESTERDAY = 2
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEarthBinding.inflate(inflater, container, false)

        binding.earthViewPager.adapter = ViewPagerAdapter(requireActivity(), context)
        setTabs()

        return binding.root
    }

    private fun setTabs() {
        TabLayoutMediator(binding.earthTabLayout, binding.earthViewPager){ tab, position ->
            tab.text = when (position) {
                EARTH_TODAY -> getString(R.string.tabToday)
                EARTH_YESTERDAY -> getString(R.string.tabYesterday)
                EARTH_DAY_BEFORE_YESTERDAY -> getString(R.string.tabBeforeYesterday)
                else -> getString(R.string.tabToday)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}