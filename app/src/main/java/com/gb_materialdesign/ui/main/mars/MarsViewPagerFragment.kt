package com.gb_materialdesign.ui.main.mars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gb_materialdesign.adapters.MarsViewPagerAdapter
import com.gb_materialdesign.databinding.FragmentMarsViewpagerBinding
import com.gb_materialdesign.model.marsPicture.Camera
import com.gb_materialdesign.model.marsPicture.Photo
import com.gb_materialdesign.ui.main.appState.AppState
import com.gb_materialdesign.ui.main.appState.AppStateRenderer
import com.gb_materialdesign.utils.getTheDateInFormat
import com.google.android.material.tabs.TabLayoutMediator

class MarsViewPagerFragment: Fragment() {

    private var _binding: FragmentMarsViewpagerBinding? = null
    private val binding get() = _binding!!

    private lateinit var parentView: View

    private lateinit var camera : Camera

    private val dataRenderer by lazy {
        AppStateRenderer(parentView) {viewModel.getPicturesOfMarsByCamera(
            camera.name, getTheDateInFormat(0))}
    }

    private val viewModel: MarsPictureViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(MarsPictureViewModel::class.java)
    }

    companion object {
        fun newInstance(camera: Camera): MarsViewPagerFragment {
            val fragment = MarsViewPagerFragment()
            fragment.arguments = Bundle().apply{
                putParcelable("camera", camera)
            }
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarsViewpagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        parentView = binding.marsViewPagerFragment

        camera = arguments?.getParcelable("camera")?: Camera()

        viewModel.getPicturesOfMarsByCamera(camera.name, getTheDateInFormat(0))
        viewModel.getLiveData().observe(viewLifecycleOwner) {
            renderData(it)
        }
    }

    private fun renderData (appState: AppState?) {
        dataRenderer.render(appState)

        when (appState) {
            is AppState.SuccessMarsPicture -> {
                val pictures = appState.marsPictures

                binding.marsViewPager.adapter = MarsViewPagerAdapter(requireActivity(),
                    context, pictures.photos)

                setTabs(pictures.photos)
            }
            else -> return
        }
    }

    private fun setTabs(photos: List<Photo>) {
        TabLayoutMediator(binding.marsTabLayout, binding.marsViewPager) {tab, position ->
            val marsPicture = photos[position]
            tab.text = marsPicture.id.toString()
        }.attach()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}