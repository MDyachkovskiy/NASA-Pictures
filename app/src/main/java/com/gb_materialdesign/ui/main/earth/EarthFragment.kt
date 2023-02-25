package com.gb_materialdesign.ui.main.earth

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.gb_materialdesign.R
import com.gb_materialdesign.adapters.ViewPagerAdapter
import com.gb_materialdesign.databinding.FragmentEarthBinding
import com.gb_materialdesign.model.earthPicture.EarthPictureResponse
import com.gb_materialdesign.ui.main.appState.AppState
import com.gb_materialdesign.ui.main.appState.AppStateRenderer
import com.google.android.material.tabs.TabLayoutMediator
import java.text.SimpleDateFormat
import java.util.*

class EarthFragment : Fragment() {

    private var _binding: FragmentEarthBinding? = null
    private val binding get() = _binding!!

    private lateinit var parentView: View

    private val viewModel: EarthPictureViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(EarthPictureViewModel::class.java)
    }

    private val dataRenderer by lazy {
        AppStateRenderer(parentView) { viewModel.getLiveData(getTheDateInFormat(0)) }
    }

    private val onPageChangeListener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {

            val tabCount = binding.earthViewPager.adapter?.itemCount
            for (i in 0 until (tabCount ?: 0)) {

                context?.let {

                    val selectedTab = binding.earthTabLayout.getTabAt(i)
                    val customTabView = selectedTab?.customView as? AppCompatTextView
                    val icon = ContextCompat.getDrawable(it, R.drawable.ic_earth)?.mutate()

                    if (i == position) {
                        val color = resources.getColor(R.color.textColorPrimaryJupiterTheme)
                        icon?.setTint(color)

                        customTabView?.setTextColor(color)
                        customTabView?.setTypeface(null, Typeface.BOLD)
                        customTabView?.setCompoundDrawablesWithIntrinsicBounds(
                            icon, null, null, null)
                    } else {
                        customTabView?.setTypeface(null, Typeface.NORMAL)
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance() = EarthFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEarthBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        parentView = binding.fragmentEarth

        binding.earthViewPager.registerOnPageChangeCallback(onPageChangeListener)

        viewModel.getLiveData(getTheDateInFormat(0)).observe(viewLifecycleOwner) {
            renderData(it)
        }
    }

    private fun renderData(appState: AppState?) {
        dataRenderer.render(appState)

        when (appState) {
            is AppState.SuccessEarthPicture -> {

                val pictures = appState.earthPictures

                binding.earthViewPager.adapter = ViewPagerAdapter(
                    requireActivity(),
                    context,
                    pictures
                )

                setTabs(pictures)
            }
            else -> return
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun getTheDateInFormat(decreaseDays: Int): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, -decreaseDays)
        val date = calendar.time
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(date)
    }

    private fun setTabs(pictures: EarthPictureResponse) {
        TabLayoutMediator(binding.earthTabLayout, binding.earthViewPager) { tab, position ->
            val picture = pictures[position]
            context?.let {

                val color = R.color.colorPrimaryGreyTheme

                val layoutInflater = LayoutInflater.from(it)
                val tabLayout = layoutInflater.inflate(R.layout.view_pager_custom_tab_earth, null)
                    .findViewById<AppCompatTextView>(R.id.tab_image_textview)
                tabLayout.text =
                    picture.date?.substring(11, 19) ?: getString(R.string.tab_layout_sometime)
                tabLayout.setTextColor(ContextCompat.getColor(it, color))

                val icon = ContextCompat.getDrawable(it, R.drawable.ic_earth)
                icon?.setTint(ContextCompat.getColor(it, color))
                tabLayout.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null)

                tab.customView = tabLayout
            }
        }.attach()
    }
}