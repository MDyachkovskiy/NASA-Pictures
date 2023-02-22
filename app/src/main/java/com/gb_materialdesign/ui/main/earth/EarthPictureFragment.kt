package com.gb_materialdesign.ui.main.earth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gb_materialdesign.R
import com.gb_materialdesign.adapters.ViewPagerAdapter
import com.gb_materialdesign.databinding.FragmentEarthBinding
import com.gb_materialdesign.ui.main.appState.AppStateRenderer
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.text.SimpleDateFormat
import java.util.*

class EarthFragment : Fragment() {

    private var _binding: FragmentEarthBinding? = null
    private val binding get() = _binding!!

    private lateinit var parentView: View

    private val viewModel: EarthPictureViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(EarthPictureViewModel::class.java)
    }

    private val dataRenderer by lazy{
        AppStateRenderer(parentView) { viewModel.getLiveData(getTheDateInFormat(0))}
    }

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        parentView = binding.fragmentEarth
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setTabs() {
        TabLayoutMediator(binding.earthTabLayout, binding.earthViewPager,
            object: TabLayoutMediator.TabConfigurationStrategy {
                override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                    tab.text = when (position) {
                        EARTH_TODAY -> getString(R.string.tabToday)
                        EARTH_YESTERDAY -> getString(R.string.tabYesterday)
                        EARTH_DAY_BEFORE_YESTERDAY -> getString(R.string.tabBeforeYesterday)
                        else -> getString(R.string.tabToday)
                    }
                }
            }).attach()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun getTheDateInFormat (decreaseDays: Int) : String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, -decreaseDays)
        val date = calendar.time
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(date)
    }
}