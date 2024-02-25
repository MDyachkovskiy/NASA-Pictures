package com.gb_materialdesign.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.gb_materialdesign.R
import com.gb_materialdesign.databinding.ActivityMainBinding
import com.test.application.core.navigation.FragmentAdder
import com.test.application.core.navigation.FragmentInteractionListener
import com.test.application.core.navigation.FragmentType
import com.test.application.core.utils.RETURN_TO_SETTINGS_KEY
import com.test.application.core.utils.THEME_KEY
import com.test.application.earth_picture.view.EarthFragment
import com.test.application.mars_picture.view.MarsFragment
import com.test.application.picture_of_the_day.view.PictureOfTheDayFragment
import com.test.application.settings.SettingsFragment
import com.test.application.space.SpaceFragment

class MainActivity : AppCompatActivity(), FragmentInteractionListener, FragmentAdder {

    private lateinit var binding: ActivityMainBinding

    companion object {
        private val DEFAULT_THEME = R.style.MyGreyTheme_GB_MaterialDesign
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyThemePreferences()
        setupUI()
        navigateToInitialFragment(savedInstanceState)
    }

    private fun navigateToInitialFragment(savedInstanceState: Bundle?) {
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        if(shouldReturnToSettings(sharedPreferences)) {
            clearReturnToSettingsFlag(sharedPreferences)
            replaceFragment(SettingsFragment())
        } else if (savedInstanceState == null) {
            replaceFragment(PictureOfTheDayFragment())
        }
    }

    private fun shouldReturnToSettings(sharedPreferences: SharedPreferences): Boolean {
        return sharedPreferences.getBoolean(RETURN_TO_SETTINGS_KEY, false)
    }

    private fun clearReturnToSettingsFlag(sharedPreferences: SharedPreferences) {
        with(sharedPreferences.edit()) {
            remove(RETURN_TO_SETTINGS_KEY)
            apply()
        }
    }

    private fun setupUI() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBottomNavigationView()
    }

    private fun applyThemePreferences() {
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val themeId = sharedPreferences.getInt(THEME_KEY, DEFAULT_THEME)
        setTheme(themeId)
    }

    private fun setBottomNavigationView() {
        binding.bottomNavigationView.selectedItemId = R.id.navigation_telescope

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.navigation_telescope -> {
                    replaceFragment(PictureOfTheDayFragment())
                    true
                }
                R.id.navigation_earth -> {
                    replaceFragment(EarthFragment())
                    true
                }
                R.id.navigation_mars -> {
                    replaceFragment(MarsFragment())
                    true
                }
                R.id.navigation_space -> {
                    replaceFragment(SpaceFragment())
                    true
                }
                R.id.navigation_settings -> {
                    replaceFragment(SettingsFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack("tag")
            .commit()
    }

    override fun setupSupportActionBar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
    }

    override fun navigateToFragment(fragmentType: FragmentType) {
        val fragment = when (fragmentType) {
            FragmentType.EARTH_FRAGMENT -> EarthFragment()
        }
        replaceFragment(fragment)
    }

    override fun openBottomNavigationDrawer() {
        val bottomNavigationDrawerFragment = BottomNavigationDrawerFragment()
        bottomNavigationDrawerFragment
            .show(supportFragmentManager, bottomNavigationDrawerFragment.tag)
    }

    override fun replaceAsteroidFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }
}