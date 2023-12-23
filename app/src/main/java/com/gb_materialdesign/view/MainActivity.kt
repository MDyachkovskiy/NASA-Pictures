package com.gb_materialdesign.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.gb_materialdesign.R
import com.gb_materialdesign.databinding.ActivityMainBinding
import com.test.application.core.navigation.FragmentAdder
import com.test.application.core.navigation.FragmentInteractionListener
import com.test.application.core.navigation.FragmentType
import com.test.application.earth_picture.EarthFragment
import com.test.application.mars_picture.view.MarsFragment
import com.test.application.picture_of_the_day.view.PictureOfTheDayFragment
import com.test.application.settings.SettingsFragment
import com.test.application.space.SpaceFragment

class MainActivity : AppCompatActivity(), FragmentInteractionListener, FragmentAdder {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.MyGreyTheme_GB_MaterialDesign)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            replaceFragment(PictureOfTheDayFragment())
        }

        setBottomNavigationView()

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

        binding.bottomNavigationView.setOnItemReselectedListener { item ->
            when (item.itemId) {
                R.id.navigation_telescope -> {
                }
                R.id.navigation_earth -> {
                }
                R.id.navigation_mars -> {
                }
                R.id.navigation_space -> {
                }
                R.id.navigation_settings -> {
                }
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
            FragmentType.ASTEROID_DETAIL_FRAGMENT -> TODO()
        }
        replaceFragment(fragment)
    }

    override fun openBottomNavigationDrawer() {
        val bottomNavigationDrawerFragment = BottomNavigationDrawerFragment()
        bottomNavigationDrawerFragment
            .show(supportFragmentManager, bottomNavigationDrawerFragment.tag)
    }

    override fun addFragmentOnTop(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }
}