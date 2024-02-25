package com.test.application.earth_picture.view

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.test.application.core.domain.earthPicture.EarthPicture
import com.test.application.core.utils.getTheDateInFormat
import com.test.application.core.view.BaseFragment
import com.test.application.earth_picture.adapter.ViewPagerAdapter
import com.test.application.earth_picture.databinding.FragmentEarthBinding
import com.test.application.earth_picture.utils.EarthPageChangeCallback
import com.test.application.earth_picture.utils.EarthTabsConfigurer
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class EarthFragment : BaseFragment<EarthPicture, FragmentEarthBinding>(
    FragmentEarthBinding::inflate
) {

    private val viewModel: EarthFragmentViewModel by viewModel()

    private lateinit var pageChangeCallback: EarthPageChangeCallback
    private lateinit var tabsConfigurer: EarthTabsConfigurer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pageChangeCallback = EarthPageChangeCallback(binding.earthTabLayout, requireContext())
        binding.earthViewPager.registerOnPageChangeCallback(pageChangeCallback)

        tabsConfigurer = EarthTabsConfigurer(
            binding.earthTabLayout, binding.earthViewPager, requireContext()
        )

        initViewModel()
    }

    private fun initViewModel() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlow.collect { appState ->
                    renderData(appState)
                }
            }
        }
        viewModel.getPicturesOfEarth(getTheDateInFormat(0))
    }

    override fun findProgressBar(): FrameLayout {
        return binding.progressBar
    }

    override fun setupData(data: EarthPicture) {
        binding.earthViewPager.adapter = ViewPagerAdapter(requireActivity(), data)
        tabsConfigurer.setupTabs(data)
    }

    override fun onDestroyView() {
        binding.earthViewPager.adapter = null
        tabsConfigurer.cleanup()
        super.onDestroyView()
    }
}