package com.gb_materialdesign.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.gb_materialdesign.databinding.FragmentPictureOfTheDayBinding
import com.gb_materialdesign.ui.main.AppState.AppState
import com.gb_materialdesign.ui.main.AppState.AppStateRenderer

class PictureOfTheDayFragment : Fragment() {

    private var _binding: FragmentPictureOfTheDayBinding? = null
    private val binding get() = _binding!!

    private lateinit var parentView: View

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
    }

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(PictureOfTheDayViewModel::class.java)
    }

    private val dataRenderer by lazy {
        AppStateRenderer(parentView) {viewModel.getPictureOfTheDay()}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPictureOfTheDayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        parentView = binding.main

        binding.includedLoadingLayout.loadingLayout.visibility = View.VISIBLE

        viewModel.getLiveData().observe(viewLifecycleOwner){
            renderData(it)
        }
    }

    private fun renderData (appState: AppState) {
        dataRenderer.render(appState)

        when (appState) {
            is AppState.Success -> {
                val url = appState.pictureOfTheDay.url
                binding.pictureOfTheDay.load(url)
            }
            else -> return
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}