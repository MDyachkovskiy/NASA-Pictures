package com.test.application.mars_picture.view

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.tabs.TabLayoutMediator
import com.test.application.core.domain.marsPicture.MarsCamera
import com.test.application.core.domain.marsPicture.MarsPhoto
import com.test.application.core.domain.marsPicture.MarsPicture
import com.test.application.core.view.BaseFragment
import com.test.application.mars_picture.databinding.FragmentMarsViewpagerBinding
import com.test.application.mars_picture.utils.MARS_CAMERA_BUNDLE_KEY
import com.test.application.mars_picture.adapter.MarsViewPagerAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class MarsViewPagerFragment: BaseFragment<MarsPicture,FragmentMarsViewpagerBinding>(
    FragmentMarsViewpagerBinding::inflate
) {

    private val marsCamera : MarsCamera by lazy {
        arguments?.getParcelable(MARS_CAMERA_BUNDLE_KEY) ?: MarsCamera()
    }

    private val viewModel: MarsFragmentViewModel by viewModel()

    companion object {
        fun newInstance(marsCamera: MarsCamera) = MarsViewPagerFragment().apply {
            arguments = Bundle().apply{ putParcelable(MARS_CAMERA_BUNDLE_KEY, marsCamera) }
        }
    }

    override fun findProgressBar(): FrameLayout {
        return binding.progressBar
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
        viewModel.getPicturesOfMarsByCamera(marsCamera.name)
    }

    private fun setTabs(photos: List<MarsPhoto>) {
        TabLayoutMediator(binding.marsTabLayout, binding.marsViewPager) {tab, position ->
            val marsPicture = photos[position]
            tab.text = marsPicture.id.toString()
        }.attach()
    }

    override fun setupData(data: MarsPicture) {
        binding.marsViewPager.adapter = MarsViewPagerAdapter(requireActivity(), data.photos)
        setTabs(data.photos)
    }

    override fun onDestroyView() {
        binding.marsViewPager.adapter = null
        super.onDestroyView()
    }
}