package com.test.application.picture_of_the_day.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
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
import com.test.application.picture_of_the_day.utils.BottomAppBarConfigurator
import com.test.application.picture_of_the_day.utils.BottomSheetDescriptionFormatter
import com.test.application.picture_of_the_day.utils.BottomSheetHeaderFormatter
import com.test.application.picture_of_the_day.utils.ChipGroupManager
import com.test.application.picture_of_the_day.utils.ImageScaleAnimator
import com.test.application.picture_of_the_day.utils.ImageScaleHandler
import com.test.application.picture_of_the_day.utils.TransitionAnimator
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PictureOfTheDayFragment : BaseFragment<PictureOfTheDay, FragmentPictureOfTheDayBinding>(
    FragmentPictureOfTheDayBinding::inflate
) {

    private val viewModel: PictureOfTheDayViewModel by viewModel()

    private var isViewVisible = false
    private var isMain = true

    private val imageScaleAnimator = ImageScaleAnimator()

    private val bottomSheetDescriptionFormatter: BottomSheetDescriptionFormatter by lazy {
        BottomSheetDescriptionFormatter(requireContext())
    }
    private val bottomSheetHeaderFormatter: BottomSheetHeaderFormatter by lazy {
        BottomSheetHeaderFormatter(requireContext())
    }
    private val bottomAppBarConfigurator: BottomAppBarConfigurator by lazy {
        BottomAppBarConfigurator(requireContext())
    }
    private val transitionAnimator = TransitionAnimator()

    private var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>? = null
    private lateinit var imageScaleHandler: ImageScaleHandler
    private lateinit var chipGroupManager: ChipGroupManager
    private lateinit var bottomSheetCallback: BottomSheetBehavior.BottomSheetCallback

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        imageScaleHandler = ImageScaleHandler(binding.pictureOfTheDay, imageScaleAnimator)
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
        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED

        bottomSheetCallback = object: BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                val isExpanded = newState == BottomSheetBehavior.STATE_EXPANDED
                binding.pictureOfTheDay.isEnabled = !isExpanded
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        }

        bottomSheetBehavior?.addBottomSheetCallback(bottomSheetCallback)
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
        chipGroupManager = ChipGroupManager(binding.chipGroup) { index ->
            viewModel.getPictureOfTheDayByDate(getTheDateInFormat(index))
        }
        chipGroupManager.setChipListeners()
    }

    override fun onDestroyView() {
        bottomSheetDescriptionFormatter.cleanup()
        bottomSheetHeaderFormatter.cleanup()
        bottomAppBarConfigurator.cleanup()
        chipGroupManager.cleanup()
        bottomSheetBehavior?.removeBottomSheetCallback(bottomSheetCallback)
        bottomSheetBehavior = null
        super.onDestroyView()
    }
}