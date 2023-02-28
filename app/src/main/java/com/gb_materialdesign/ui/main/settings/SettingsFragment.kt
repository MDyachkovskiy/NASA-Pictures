package com.gb_materialdesign.ui.main.settings

import android.content.Context
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gb_materialdesign.R
import com.gb_materialdesign.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private var currentThemeId = R.style.MyGreyTheme_GB_MaterialDesign

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.setTheme(currentThemeId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        val context: Context = ContextThemeWrapper(activity, currentThemeId)
        val localInflater = inflater.cloneInContext(context)
        _binding = FragmentSettingsBinding.inflate(localInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.jupiterStyle.setOnClickListener{
            currentThemeId = R.style.JupiterTheme_GB_MaterialDesign
            activity?.recreate()
        }

        binding.marsStyle.setOnClickListener{
            currentThemeId = R.style.MarsTheme_GB_MaterialDesign
            activity?.recreate()
        }

        binding.moonStyle.setOnClickListener{
            currentThemeId = R.style.MoonTheme_GB_MaterialDesign
            activity?.recreate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}