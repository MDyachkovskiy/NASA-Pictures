package com.gb_materialdesign.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.gb_materialdesign.R
import com.gb_materialdesign.databinding.FragmentPictureOfTheDayBinding
import com.gb_materialdesign.model.PictureOfTheDayResponse
import com.gb_materialdesign.ui.main.AppState.AppState
import com.gb_materialdesign.ui.main.AppState.AppStateRenderer
import com.google.android.material.bottomsheet.BottomSheetBehavior

private const val WIKIPEDIA_DOMAIN = "https://en.wikipedia.org/wiki/"

class PictureOfTheDayFragment : Fragment() {

    private var _binding: FragmentPictureOfTheDayBinding? = null
    private val binding get() = _binding!!

    private lateinit var parentView: View

    private lateinit var bottomSheet: View

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
    }

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(PictureOfTheDayViewModel::class.java)
    }

    private val dataRenderer by lazy {
        AppStateRenderer(parentView) { viewModel.getPictureOfTheDay() }
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

        bottomSheet = view.findViewById<View>(R.id.bottom_sheet_container)

        binding.includedLoadingLayout.loadingLayout.visibility = View.VISIBLE
        setBottomSheetBehavior(bottomSheet as ConstraintLayout)

        viewModel.getLiveData().observe(viewLifecycleOwner) {
            renderData(it)
        }

        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(
                    "$WIKIPEDIA_DOMAIN${
                        binding.inputEditText.text.toString()
                    }"
                )
            })
        }
    }

    private fun renderData(appState: AppState) {
        dataRenderer.render(appState)

        when (appState) {
            is AppState.Success -> {
                displayData(appState.pictureOfTheDay)
            }
            else -> return
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun displayData(data: PictureOfTheDayResponse) {
        with(binding) {

            pictureOfTheDay.load(data.url) {
                lifecycle(this@PictureOfTheDayFragment)
                error(R.drawable.ic_load_error_vector)
                placeholder(R.drawable.ic_no_photo_vector)
                crossfade(true)
            }
        }

        bottomSheet.findViewById<TextView>(R.id.bottomSheetDescriptionHeader).text = data.title

        bottomSheet.findViewById<TextView>(R.id.bottomSheetDescription).text = data.explanation

    }
}