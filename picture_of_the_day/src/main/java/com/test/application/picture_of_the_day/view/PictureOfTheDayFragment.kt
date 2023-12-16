package com.test.application.picture_of_the_day.view

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.text.style.*
import android.view.*
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.transition.Fade
import androidx.transition.Slide
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.gb_materialdesign.MainActivity
import com.gb_materialdesign.ui.main.earth.EarthFragment
import com.gb_materialdesign.ui.main.navigationDrawer.BottomNavigationDrawerFragment
import com.gb_materialdesign.utils.getTheDateInFormat
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.test.application.core.domain.PictureOfTheDay
import com.test.application.core.utils.WIKIPEDIA_DOMAIN
import com.test.application.core.view.BaseFragment
import com.test.application.picture_of_the_day.R
import com.test.application.picture_of_the_day.databinding.FragmentPictureOfTheDayBinding
import kotlinx.coroutines.launch


class PictureOfTheDayFragment: BaseFragment<PictureOfTheDay, FragmentPictureOfTheDayBinding> (
    FragmentPictureOfTheDayBinding::inflate
) {

    private val viewModel: PictureOfTheDayViewModel by viewModel()

    private var isImageScaled = false



    private val imageScaleAnimator = ImageScaleAnimator()
    private val bottomSheetDescriptionFormatter = BottomSheetDescriptionFormatter(requireContext())
    private val bottomSheetHeaderFormatter = BottomSheetHeaderFormatter(requireContext())

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
        val options = RequestOptions()
            .error(R.drawable.ic_load_error_vector)
            .placeholder(R.drawable.ic_no_photo_vector)

        activity?.let {
            Glide.with(it)
                .load(data.url)
                .apply(options)
                .transition(DrawableTransitionOptions.withCrossFade())
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.imageProgressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.imageProgressBar.visibility = View.GONE
                        displayViewElements()
                        return false
                    }
                })
                .into(binding.pictureOfTheDay)

        }
        setTextFormatHeader(data.title)
        setTextFormatDescription(data.explanation)
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
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

        val chipTransition = TransitionSet().apply {
            duration = 1500
            ordering = TransitionSet.ORDERING_SEQUENTIAL
            val fade = Slide(Gravity.END)
            this.addTransition(fade)
        }

        val textInputTransition = TransitionSet().apply {
            duration = 2000
            val fade = Fade()
            this.addTransition(fade)
        }

        if (isViewVisible) {
            return
        } else {
            with(binding) {
                TransitionManager.beginDelayedTransition(chipGroup, chipTransition)
                chipToday.visibility = View.VISIBLE
                chipYesterday.visibility = View.VISIBLE
                chipDayBeforeYesterday.visibility = View.VISIBLE

                TransitionManager.beginDelayedTransition(inputLayout, textInputTransition)
                inputLayout.visibility = View.VISIBLE

                TransitionManager.beginDelayedTransition(root)
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
            if (isMain) {
                isMain = false
                with(binding) {
                    bottomAppBar.navigationIcon = null

                    bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END

                    fab.setImageDrawable(context?.let {
                        ContextCompat.getDrawable(
                            it,
                            R.drawable.ic_back_fab
                        )
                    })

                    bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
                }
            } else {
                isMain = true
                with(binding) {
                    bottomAppBar.navigationIcon = context?.let {
                        ContextCompat.getDrawable(it, R.drawable.ic_hamburger_menu_bottom_bar)
                    }

                    bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER

                    fab.setImageDrawable(context?.let {
                        ContextCompat.getDrawable(
                            it,
                            R.drawable.ic_plus_fab
                        )
                    })

                    bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
                }
            }
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