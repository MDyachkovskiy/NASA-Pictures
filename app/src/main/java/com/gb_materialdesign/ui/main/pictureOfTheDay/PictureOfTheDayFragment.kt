package com.gb_materialdesign.ui.main.pictureOfTheDay

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.view.*
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
import com.bumptech.glide.request.target.Target
import com.gb_materialdesign.MainActivity
import com.gb_materialdesign.R
import com.gb_materialdesign.databinding.FragmentPictureOfTheDayBinding
import com.gb_materialdesign.model.pictureOfTheDay.PictureOfTheDayResponse
import com.gb_materialdesign.ui.main.appState.AppState
import com.gb_materialdesign.ui.main.appState.AppStateRenderer
import com.gb_materialdesign.ui.main.earth.EarthFragment
import com.gb_materialdesign.ui.main.navigationDrawer.BottomNavigationDrawerFragment
import com.gb_materialdesign.utils.WIKIPEDIA_DOMAIN
import com.gb_materialdesign.utils.getTheDateInFormat
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior


class PictureOfTheDayFragment : Fragment() {

    private var _binding: FragmentPictureOfTheDayBinding? = null
    private val binding get() = _binding!!

    private lateinit var parentView: View

    private lateinit var bottomSheet: View

    private var isImageScaled = false

    private var pivotX = 0.5f
    private var pivotY = 0.5f

    private var isViewVisible = false

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    companion object {
        private var isMain = true
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

        bottomSheet = view.findViewById(R.id.bottom_sheet_container)

        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))
        
        binding.pictureOfTheDay.setOnTouchListener { v, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN){
                v.performClick()
                if (!isImageScaled) {
                    pivotX = motionEvent.x / v.width
                    pivotY = motionEvent.y / v.height
                    val animation = ScaleAnimation(
                        1f, 3f,
                        1f, 3f,
                        Animation.RELATIVE_TO_SELF, pivotX,
                        Animation.RELATIVE_TO_SELF, pivotY)
                    animation.duration = 1000
                    animation.fillAfter = true
                    v.startAnimation(animation)
                    isImageScaled = !isImageScaled
                } else {
                    val animation = ScaleAnimation(
                        3f, 1f,
                        3f, 1f,
                    Animation.RELATIVE_TO_SELF, pivotX,
                    Animation.RELATIVE_TO_SELF, pivotY)
                    animation.duration = 1000
                    animation.fillAfter = true
                    v.startAnimation(animation)
                    isImageScaled = !isImageScaled
                }
            }
            true
        }

        viewModel.getLiveData().observe(viewLifecycleOwner) {
            renderData(it)
        }

        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(
                    "$WIKIPEDIA_DOMAIN${
                        binding.inputEditText.text.toString()}"
                )
            })
        }

        setBottomAppBar(view)

        setChipGroup()

    }

    private fun renderData(appState: AppState) {
        dataRenderer.render(appState)

        when (appState) {
            is AppState.SuccessTelescope -> {
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

        val options = RequestOptions()
            .error(R.drawable.ic_load_error_vector)
            .placeholder(R.drawable.ic_no_photo_vector)

        activity?.let{
            Glide.with(it)
                .load(data.url)
                .apply(options)
                .transition(DrawableTransitionOptions.withCrossFade())
                .listener (object: RequestListener<Drawable> {
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

        setTextFormat(data)

    }

    private fun setTextFormat(data: PictureOfTheDayResponse) {
        val header = data.title
        val spannableString = SpannableString(header)
        val startIndex = 0
        val flag = 0
        val endIndex = header.length
        val color = ContextCompat.getColor(requireContext(), R.color.colorPrimaryMarsTheme)
        spannableString.setSpan(BackgroundColorSpan(color),startIndex,endIndex,flag)

        bottomSheet.findViewById<TextView>(R.id.bottom_sheet_description_header).text = spannableString

        bottomSheet.findViewById<TextView>(R.id.bottom_sheet_description).text = data.explanation
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
            with(binding){
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
        when(item.itemId){
            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(it.supportFragmentManager,"tag")
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
        binding.fab.setOnClickListener{
            if(isMain) {
                isMain = false
                with(binding) {
                    bottomAppBar.navigationIcon = null

                    bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END

                    fab.setImageDrawable(context?.let { ContextCompat.getDrawable(it,
                        R.drawable.ic_back_fab) })

                    bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
                }
            } else {
                isMain = true
                with(binding) {
                    bottomAppBar.navigationIcon = context?.let{
                            ContextCompat.getDrawable(it, R.drawable.ic_hamburger_menu_bottom_bar) }

                    bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER

                    fab.setImageDrawable(context?.let { ContextCompat.getDrawable(it,
                        R.drawable.ic_plus_fab) })

                    bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
                }
            }
        }
    }

    private fun setChipGroup() {

        binding.chipToday.setOnClickListener{
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