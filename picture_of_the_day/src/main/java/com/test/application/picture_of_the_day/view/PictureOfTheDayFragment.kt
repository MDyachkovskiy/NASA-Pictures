package com.test.application.picture_of_the_day.view

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.style.*
import android.view.*
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import coil.load
import com.gb_materialdesign.MainActivity
import com.gb_materialdesign.ui.main.earth.EarthFragment
import com.gb_materialdesign.ui.main.navigationDrawer.BottomNavigationDrawerFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.test.application.core.domain.PictureOfTheDay
import com.test.application.core.utils.WIKIPEDIA_DOMAIN
import com.test.application.core.view.BaseFragment
import com.test.application.picture_of_the_day.R
import com.test.application.picture_of_the_day.databinding.FragmentPictureOfTheDayBinding
import com.test.application.picture_of_the_day.utils.getTheDateInFormat
import com.test.application.picture_of_the_day.view.utils.BottomAppBarConfigurator
import com.test.application.picture_of_the_day.view.utils.BottomSheetDescriptionFormatter
import com.test.application.picture_of_the_day.view.utils.BottomSheetHeaderFormatter
import com.test.application.picture_of_the_day.view.utils.ImageScaleAnimator
import com.test.application.picture_of_the_day.view.utils.TransitionAnimator
import kotlinx.coroutines.launch


class PictureOfTheDayFragment: BaseFragment<PictureOfTheDay, FragmentPictureOfTheDayBinding> (
    FragmentPictureOfTheDayBinding::inflate
) {

    private val viewModel: PictureOfTheDayViewModel by viewModel()

    private var isImageScaled = false

    private val imageScaleAnimator = ImageScaleAnimator()
    private val bottomSheetDescriptionFormatter = BottomSheetDescriptionFormatter(requireContext())
    private val bottomSheetHeaderFormatter = BottomSheetHeaderFormatter(requireContext())
    private val bottomAppBarConfigurator = BottomAppBarConfigurator(requireContext())
    private val transitionAnimator = TransitionAnimator()

    private var isViewVisible = false

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    companion object {
        private var isMain = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViewModel()
        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))
        handlePictureScaleAnimation()
        setInputLayout()
        setBottomAppBar(view)
        setChipGroup()
    }

    private fun setInputLayout() {
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

    private fun handlePictureScaleAnimation() {
        binding.pictureOfTheDay.setOnTouchListener { v, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                v.performClick()
                val pivotX = motionEvent.x / v.width
                val pivotY = motionEvent.y / v.height
                val scaleFrom = if(!isImageScaled) 1f else 3f
                val scaleTo = if(!isImageScaled) 3f else 1f
                imageScaleAnimator.animateScale(v, scaleFrom, scaleTo, pivotX, pivotY, 1000)
                isImageScaled = !isImageScaled
            }
            true
        }
    }

    private fun initViewModel() {

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlow.collect {appState ->
                    renderData(appState)
                }
            }
        }
        viewModel.getPictureOfTheDay()
    }

    override fun findProgressBar(): FrameLayout {
        TODO("Not yet implemented")
    }

    override fun setupData(data: PictureOfTheDay) {
        loadImage(data.url)
        setTextFormatHeader(data.title)
        setTextFormatDescription(data.explanation)
    }

    private fun loadImage(imageUrl: String) {
        binding.pictureOfTheDay.load(imageUrl) {
            crossfade(true)
            placeholder(com.test.application.core.R.drawable.ic_no_photo_vector)
            error(com.test.application.core.R.drawable.ic_load_error_vector)
            listener(
                onError = {_,_ ->
                    binding.imageProgressBar.visibility = View.GONE
                },
                onSuccess = {_,_ ->
                    binding.imageProgressBar.visibility = View.GONE
                    displayViewElements()
                }
            )
        }
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun setTextFormatDescription(description: String) {
        val bottomSheetDescription = binding.bottomSheetLayout.bottomSheetDescription
        bottomSheetDescription.text = bottomSheetDescriptionFormatter.formatDescription(description)
    }

    private fun setTextFormatHeader(header: String) {
        binding.bottomSheetLayout.bottomSheetDescriptionHeader.text =
            bottomSheetHeaderFormatter.formatHeader(header)
    }

    private fun displayViewElements() {
        if(!isViewVisible) {
            with(binding) {
                transitionAnimator.applyTransitionOnChips(chipGroup)
                chipToday.visibility = View.VISIBLE
                chipYesterday.visibility = View.VISIBLE
                chipDayBeforeYesterday.visibility = View.VISIBLE

                transitionAnimator.applyTransitionOnInputLayout(inputLayout)
                inputLayout.visibility = View.VISIBLE

                transitionAnimator.applyTransitionOnViewGroup(root)
            }
            isViewVisible = !isViewVisible
         }
    }

    private fun setBottomAppBar(view: View) {
        val context = activity as MainActivity
        context.setSupportActionBar(view.findViewById(R.id.bottom_app_bar))
        setHasOptionsMenu(true)
        setFloatingActionButton()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(it.supportFragmentManager, "tag")
                }
            }
            R.id.app_bar_fav -> requireActivity().supportFragmentManager.beginTransaction()
                .hide(this)
                .add(R.id.container, EarthFragment.newInstance())
                .addToBackStack("tag")
                .commit()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setFloatingActionButton() {
        binding.fab.setOnClickListener {
            isMain = !isMain
            bottomAppBarConfigurator.setupBottomAppBar(binding.bottomAppBar, binding.fab, isMain)
        }
    }

    private fun setChipGroup() {

        binding.chipToday.setOnClickListener {
            viewModel.getPictureOfTheDayByDate(getTheDateInFormat(0))
        }

        binding.chipYesterday.setOnClickListener {
            viewModel.getPictureOfTheDayByDate(getTheDateInFormat(1))
        }

        binding.chipDayBeforeYesterday.setOnClickListener {
            viewModel.getPictureOfTheDayByDate(getTheDateInFormat(2))
        }
    }
}