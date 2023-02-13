package com.gb_materialdesign.ui.main.pictureOfTheDay

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.gb_materialdesign.MainActivity
import com.gb_materialdesign.R
import com.gb_materialdesign.databinding.FragmentPictureOfTheDayBinding
import com.gb_materialdesign.model.PictureOfTheDayResponse
import com.gb_materialdesign.ui.main.appState.AppState
import com.gb_materialdesign.ui.main.appState.AppStateRenderer
import com.gb_materialdesign.ui.main.navigationDrawer.BottomNavigationDrawerFragment
import com.gb_materialdesign.utils.toast
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.text.SimpleDateFormat
import java.util.*

private const val WIKIPEDIA_DOMAIN = "https://en.wikipedia.org/wiki/"

class PictureOfTheDayFragment : Fragment() {

    private var _binding: FragmentPictureOfTheDayBinding? = null
    private val binding get() = _binding!!

    private lateinit var parentView: View

    private lateinit var bottomSheet: View

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
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

        binding.includedLoadingLayout.loadingLayout.visibility = View.VISIBLE
        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))

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
            R.id.app_bar_fav -> toast("Favourite")
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

    private fun getTheDateInFormat (decreaseDays: Int) : String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, -decreaseDays)
        val date = calendar.time
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(date)
    }
}