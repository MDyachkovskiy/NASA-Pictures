package com.test.application.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.test.application.settings.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val localInflater = inflater.cloneInContext(context)
        _binding = FragmentSettingsBinding.inflate(localInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.jupiterStyle.setOnClickListener{
            changeTheme(R.style.JupiterTheme_GB_MaterialDesign)
        }

        binding.marsStyle.setOnClickListener{
            changeTheme(R.style.MarsTheme_GB_MaterialDesign)
        }

        binding.moonStyle.setOnClickListener{
            changeTheme(R.style.MoonTheme_GB_MaterialDesign)
        }
    }

    private fun changeTheme(themeId: Int) {
        val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putInt("theme", themeId)
            putBoolean("returnToSettings", true)
            apply()
        }
        requireActivity().recreate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}