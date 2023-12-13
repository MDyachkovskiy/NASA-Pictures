package com.test.application.picture_of_the_day.view

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.*
import android.view.*
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
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

    private var pivotX = 0.5f
    private var pivotY = 0.5f

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
                if (!isImageScaled) {
                    pivotX = motionEvent.x / v.width
                    pivotY = motionEvent.y / v.height
                    val animation = ScaleAnimation(
                        1f, 3f,
                        1f, 3f,
                        Animation.RELATIVE_TO_SELF, pivotX,
                        Animation.RELATIVE_TO_SELF, pivotY
                    )
                    animation.duration = 1000
                    animation.fillAfter = true
                    v.startAnimation(animation)
                    isImageScaled = !isImageScaled
                } else {
                    val animation = ScaleAnimation(
                        3f, 1f,
                        3f, 1f,
                        Animation.RELATIVE_TO_SELF, pivotX,
                        Animation.RELATIVE_TO_SELF, pivotY
                    )
                    animation.duration = 1000
                    animation.fillAfter = true
                    v.startAnimation(animation)
                    isImageScaled = !isImageScaled
                }
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
        val indexOfLastCharOnFirstLine = 40
        val newDescription = description.substring(0, indexOfLastCharOnFirstLine) + "\n" +
                description.substring(indexOfLastCharOnFirstLine)


        val spannableDescription = SpannableString(newDescription)
        val startIndex = 0
        val flag = 0
        val endIndex = description.length
        val color = ContextCompat.getColor(requireContext(), R.color.colorPrimaryMarsTheme)

        val stripeWidthInPx = 20
        val gapWidthInPx = 100

        spannableDescription.setSpan(
            QuoteSpan(color, stripeWidthInPx, gapWidthInPx),
            startIndex, indexOfLastCharOnFirstLine, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        val lineHeightInPx = 100
        spannableDescription.setSpan(
            LineHeightSpan.Standard(lineHeightInPx),
            startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        val fontSizeMultiplier = 2.0f
        spannableDescription.setSpan(
            RelativeSizeSpan(fontSizeMultiplier),
            startIndex, startIndex + 1, flag
        )

        val bitmap = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.ic_baseline_favorite_border_24
        )!!.toBitmap()
        for (i in newDescription.indices) {
            if (newDescription[i] == 'o') {
                spannableDescription.setSpan(
                    ImageSpan(requireContext(), bitmap, DynamicDrawableSpan.ALIGN_BOTTOM),
                    i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            } else if (newDescription[i] == 'O') {
                spannableDescription.setSpan(
                    ImageSpan(requireContext(), bitmap, DynamicDrawableSpan.ALIGN_BOTTOM),
                    i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }

        val resultStar = newDescription.indexesOf("star")
        if (resultStar.isNotEmpty()) {
            val textColor = ContextCompat.getColor(requireContext(), R.color.teal_700)
            resultStar.forEach {
                spannableDescription.setSpan(
                    ForegroundColorSpan(textColor), it,
                    it + 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                spannableDescription.setSpan(UnderlineSpan(), it, it + 4, flag)
            }
        }

        val resultStars = newDescription.indexesOf("stars")
        if (resultStars.isNotEmpty()) {
            resultStars.forEach {
                val textColor = ContextCompat.getColor(requireContext(), R.color.teal_700)
                spannableDescription.setSpan(
                    ForegroundColorSpan(textColor), it,
                    it + 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                spannableDescription.setSpan(UnderlineSpan(), it, it + 5, flag)
            }
        }

        bottomSheetDescription.text = spannableDescription
    }

    private fun String.indexesOf(substr: String, ignoreCase: Boolean = true): List<Int> =
        (if (ignoreCase) Regex(substr, RegexOption.IGNORE_CASE) else Regex(substr))
            .findAll(this).map { it.range.first }.toList()

    private fun setTextFormatHeader(header: String) {
        val spannableHeader = SpannableString(header)
        val startIndex = 0
        val flag = 0
        val endIndex = header.length

        val color = ContextCompat.getColor(requireContext(), R.color.colorPrimaryMarsTheme)
        spannableHeader.setSpan(BackgroundColorSpan(color), startIndex, endIndex, flag)

        val proportion = 1.5f
        spannableHeader.setSpan(ScaleXSpan(proportion), startIndex, endIndex, flag)

        binding.bottomSheetLayout.bottomSheetDescriptionHeader.text = spannableHeader

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