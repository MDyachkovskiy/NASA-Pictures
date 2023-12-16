package com.test.application.core.navigation

import androidx.appcompat.widget.Toolbar

interface FragmentInteractionListener {
    fun setupSupportActionBar(toolbar: Toolbar)
    fun navigateToFragment(fragmentType: FragmentType)
    fun openBottomNavigationDrawer()
}