package com.test.application.picture_of_the_day.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.test.application.core.domain.pictureOfTheDay.PictureOfTheDay
import com.test.application.core.navigation.FragmentInteractionListener
import com.test.application.core.utils.WIKIPEDIA_DOMAIN
import com.test.application.core.utils.getTheDateInFormat
import com.test.application.core.view.BaseFragment
import com.test.application.picture_of_the_day.databinding.FragmentPictureOfTheDayBinding
import com.test.application.picture_of_the_day.view.utils.BottomAppBarConfigurator
import com.test.application.picture_of_the_day.view.utils.BottomSheetDescriptionFormatter
import com.test.application.picture_of_the_day.view.utils.BottomSheetHeaderFormatter
import com.test.application.picture_of_the_day.view.utils.ImageScaleAnimator
import com.test.application.picture_of_the_day.view.utils.TransitionAnimator
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PictureOfTheDayFragment : BaseFragment<PictureOfTheDay, FragmentPictureOfTheDayBinding>(
    FragmentPictureOfTheDayBinding::inflate
) {

    private val viewModel: PictureOfTheDayViewModel by viewModel()

    private var isImageScaled = false
    private var isViewVisible = false
    private var isMain = true

    private val imageScaleAnimator = ImageScaleAnimator()
    private lateinit var bottomSheetDescriptionFormatter: BottomSheetDescriptionFormatter
    private lateinit var bottomSheetHeaderFormatter: BottomSheetHeaderFormatter
    private lateinit var bottomAppBarConfigurator: BottomAppBarConfigurator
    private val transitionAnimator = TransitionAnimator()

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomSheetDescriptionFormatter = BottomSheetDescriptionFormatter(requireContext())
        bottomSheetHeaderFormatter = BottomSheetHeaderFormatter(requireContext())
        bottomAppBarConfigurator = BottomAppBarConfigurator(requireContext())

        initViewModel()
        setBottomSheetBehavior(binding.bottomSheetLayout.bottomSheetContainer)
        handlePictureScaleAnimation()
        setInputLayout()
        setBottomAppBar()
        setChipGroup()
    }

    override fun onResume() {
        super.onResume()
        isViewVisible = false
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
        var lastScale = 1f
        var lastPivotX = 0.5f
        var lastPivotY = 0.5f

        binding.pictureOfTheDay.setOnTouchListener { v, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                v.performClick()

                if (!isImageScaled) {
                    lastPivotX = motionEvent.x / v.width
                    lastPivotY = motionEvent.y / v.height
                }

                val scaleFrom = lastScale
                val scaleTo = if (!isImageScaled) 3f else 1f

                imageScaleAnimator.animateScale(v, scaleFrom, scaleTo, lastPivotX, lastPivotY, 1000)
                isImageScaled = !isImageScaled
                lastScale = scaleTo
            }
            true
        }
    }

    private fun initViewModel() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlow.collect { appState ->
                    renderData(appState)
                }
            }
        }
        viewModel.getPictureOfTheDay()
    }

    override fun findProgressBar(): FrameLayout {
        return binding.progressBar
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
                onError = { _, _ ->
                    binding.progressBar.visibility = View.GONE
                },
                onSuccess = { _, _ ->
                    binding.progressBar.visibility = View.GONE
                    displayViewElements()
                }
            )
        }
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        bottomSheetBehavior.addBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                val isExpanded = newState == BottomSheetBehavior.STATE_EXPANDED
                binding.pictureOfTheDay.isEnabled = !isExpanded
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

        })
    }

    private fun setTextFormatDescription(description: String) {
        val bottomSheetDescription = binding.bottomSheetLayout.bottomSheetDescription
        bottomSheetDescription.text = bottomSheetDescriptionFormatter.formatDescription(description)
    }

    private fun setTextFormatHeader(header: String) {
        binding.bottomSheetLayout.bottomSheetDescriptionHeader.text =
            bottomSheetHeaderFormatter.formatHeader(header)
    }

    private fun displayViewElements() {
        if (!isViewVisible) {
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

    private fun setBottomAppBar() {
        (requireActivity() as FragmentInteractionListener).setupSupportActionBar(binding.bottomAppBar)
        setHasOptionsMenu(true)
        setFloatingActionButton()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                (requireActivity() as FragmentInteractionListener)
                    .openBottomNavigationDrawer()
            }
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