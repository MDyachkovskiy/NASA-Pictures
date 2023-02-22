package com.gb_materialdesign.ui.main.earth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gb_materialdesign.adapters.ViewPagerAdapter
import com.gb_materialdesign.databinding.FragmentEarthBinding
import com.gb_materialdesign.ui.main.appState.AppState
import com.gb_materialdesign.ui.main.appState.AppStateRenderer
import java.text.SimpleDateFormat
import java.util.*

class EarthFragment : Fragment() {

    private var _binding: FragmentEarthBinding? = null
    private val binding get() = _binding!!

    private lateinit var parentView: View

    private val viewModel: EarthPictureViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(EarthPictureViewModel::class.java)
    }

    private val dataRenderer by lazy{
        AppStateRenderer(parentView) { viewModel.getLiveData(getTheDateInFormat(0))}
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

        viewModel.getLiveData(getTheDateInFormat(0)).observe(viewLifecycleOwner){
            renderData(it)
        }
    }

    private fun renderData(appState: AppState?) {
        dataRenderer.render(appState)

        when(appState) {
            is AppState.SuccessEarthPicture -> {
                binding.earthViewPager.adapter = ViewPagerAdapter(requireActivity(),
                    context,
                appState.earthPictures)
            }
            else -> return
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun getTheDateInFormat (decreaseDays: Int) : String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, -decreaseDays)
        val date = calendar.time
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(date)
    }
}