package com.gb_materialdesign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.gb_materialdesign.databinding.ActivityMainBinding
import com.gb_materialdesign.ui.main.earth.EarthFragment
import com.gb_materialdesign.ui.main.pictureOfTheDay.PictureOfTheDayFragment
import com.gb_materialdesign.ui.main.settings.SettingsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
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
                    replaceFragment(EarthFragment())
                    true
                }
                R.id.navigation_weather -> {
                    replaceFragment(EarthFragment())
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
                R.id.navigation_weather -> {
                }
                R.id.navigation_settings -> {
                }
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack("tag")
            .commit()
    }
}